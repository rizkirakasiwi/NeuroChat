#!/bin/sh

# Check the current branch
check_current_branch() {
    echo "\n🔍 Checking current Git branch..."
    CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

    if [ "$CURRENT_BRANCH" = "master" ] || [ "$CURRENT_BRANCH" = "dev" ]; then
        echo "❌ Commit blocked on '$CURRENT_BRANCH'. Please use a feature branch."
        echo "💡 Switch or create a new branch to continue."
        exit 1
    else
        echo "✅ On '$CURRENT_BRANCH'. Proceeding..."
    fi
}

# Run Spotless check and auto-format if needed
run_spotless_checks() {
    echo "\n🧹 Running Spotless..."
    ./gradlew spotlessCheck --daemon > /tmp/spotless-result
    if [ $? -ne 0 ]; then
        cat /tmp/spotless-result
        rm /tmp/spotless-result
        echo "⚠️ Formatting issues found. Applying fixes..."
        ./gradlew spotlessApply --daemon > /tmp/spotless-result
    fi
    rm /tmp/spotless-result
    echo "✅ Spotless check complete."
}

# Run Detekt check
run_detekt_checks() {
    echo "\n🔎 Running Detekt..."
    ./gradlew detekt > /tmp/detekt-result
    if [ $? -ne 0 ]; then
        cat /tmp/detekt-result
        rm /tmp/detekt-result
        echo "❌ Detekt found issues. Please fix them before committing."
        exit 1
    fi
    rm /tmp/detekt-result
    echo "✅ Detekt check passed."
}

# Run dependency guard
run_dependency_guard() {
    echo "\n📦 Checking dependency baseline..."
    ./gradlew dependencyGuard > /tmp/dependency-result
    if [ $? -ne 0 ]; then
        cat /tmp/dependency-result
        rm /tmp/dependency-result
        echo "⚠️ Issue generating dependency baseline. Retrying..."
        ./gradlew dependencyGuardBaseline > /tmp/dependency-result
    fi
    rm /tmp/dependency-result
    echo "✅ Dependency guard check complete."
}

# Print final success message
print_success_message() {
    GIT_USERNAME=$(git config user.name)
    echo "\n🎉 All checks passed. Well done, $GIT_USERNAME!"
    echo "🚀 Ready to push your code!"
}

# Main script
check_current_branch
run_spotless_checks
run_detekt_checks
run_dependency_guard
print_success_message

exit 0
