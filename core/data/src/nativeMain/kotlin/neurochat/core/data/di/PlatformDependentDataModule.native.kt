/*
 * Copyright © 2025 NeuroChat. All rights reserved.
 *
 * This software is proprietary and confidential. Unauthorized copying, modification,
 * distribution, or use of this software is strictly prohibited.
 */
package neurochat.core.data.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val getPlatformDataModule: PlatformDependentDataModule
    get() = NativePlatformDependentDataModule()

actual val platformModule: Module
    get() =
        module {
            single<PlatformDependentDataModule> { getPlatformDataModule }
        }
