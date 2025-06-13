#!/bin/sh

# Check current branch
check_current_branch() {
    echo "🔍 Checking current Git branch..."
    CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

    if [ "$CURRENT_BRANCH" = "master" ] || [ "$CURRENT_BRANCH" = "dev" ]; then
        echo "❌ Commit blocked on '$CURRENT_BRANCH'. Use a feature branch instead."
        exit 1
    fi

    echo "✅ On '$CURRENT_BRANCH'. Proceeding..."
}

# Run Spotless
run_spotless_checks() {
    echo "🧹 Running Spotless format check..."
    ./gradlew spotlessApply --no-configuration-cache --daemon > /tmp/spotless-result
    if [ $? -ne 0 ]; then
        cat /tmp/spotless-result
        rm /tmp/spotless-result
        echo "❌ Spotless found issues. Please fix them before committing."
        exit 1
    fi
    rm /tmp/spotless-result
    echo "✅ Spotless formatting applied successfully."
}

# Run Dependency Guard
run_dependency_guard() {
    echo "📦 Generating dependency baseline..."
    ./gradlew dependencyGuardBaseline > /tmp/dependency-result
    if [ $? -ne 0 ]; then
        cat /tmp/dependency-result
        rm /tmp/dependency-result
        echo "❌ Failed to generate dependency baseline."
        exit 1
    fi
    rm /tmp/dependency-result
    echo "✅ Dependency baseline generated successfully."
}

# Run Detekt
run_detekt_checks() {
    echo "🔎 Running Detekt with auto format..."
    ./gradlew detekt --auto-correct > /tmp/detekt-result
    if [ $? -ne 0 ]; then
        cat /tmp/detekt-result
        rm /tmp/detekt-result
        echo "❌ Detekt found issues. Please review and fix them manually."
        exit 1
    fi
    rm /tmp/detekt-result
    echo "✅ Detekt check passed."
}

# Success message
print_success_message() {
    GIT_USERNAME=$(git config user.name)
    echo "🎉 All checks passed. Great job, $GIT_USERNAME!"
    echo "🚀 Ready to commit and push your code."
}

# Main
check_current_branch
run_spotless_checks
run_detekt_checks
run_dependency_guard
print_success_message

exit 0
