/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.NetworkRequest.Builder
import androidx.core.content.getSystemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import neurochat.core.data.utils.NetworkMonitor

internal class ConnectivityManagerNetworkMonitor(
    private val context: Context,
    ioDispatcher: CoroutineDispatcher,
) : NetworkMonitor {
    override val isOnline: Flow<Boolean> =
        callbackFlow {
            val connectivityManager = context.getSystemService<ConnectivityManager>()
            if (connectivityManager == null) {
                channel.trySend(false)
                channel.close()
                return@callbackFlow
            }

            /**
             * The callback's methods are invoked on changes to *any* network matching the [NetworkRequest],
             * not just the active network. So we can simply track the presence (or absence) of such [Network].
             */
            val callback =
                object : NetworkCallback() {
                    private val networks = mutableSetOf<Network>()

                    override fun onAvailable(network: Network) {
                        networks += network
                        channel.trySend(true)
                    }

                    override fun onLost(network: Network) {
                        networks -= network
                        channel.trySend(networks.isNotEmpty())
                    }
                }
            val request =
                Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build()
            connectivityManager.registerNetworkCallback(request, callback)
            /**
             * Sends the latest connectivity status to the underlying channel.
             */
            channel.trySend(connectivityManager.isCurrentlyConnected())

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
            .flowOn(ioDispatcher)
            .conflate()

    private fun ConnectivityManager.isCurrentlyConnected() = activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
}
