#!/bin/sh

# Check current branch
check_current_branch() {
    echo "\n🔍 Checking current Git branch...\n"
    CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

    if [ "$CURRENT_BRANCH" = "master" ] || [ "$CURRENT_BRANCH" = "dev" ]; then
        echo "❌ Commit blocked on '$CURRENT_BRANCH'. Use a feature branch instead.\n"
        exit 1
    fi

    echo "✅ On '$CURRENT_BRANCH'. Proceeding...\n"
}

# Run Spotless
run_spotless_checks() {
    echo "\n🧹 Running Spotless format check...\n"
    ./gradlew spotlessApply --no-configuration-cache --daemon > /tmp/spotless-result
    if [ $? -ne 0 ]; then
        cat /tmp/spotless-result
        rm /tmp/spotless-result
        echo "\n❌ Spotless found issues. Please fix them before committing.\n"
        exit 1
    fi
    rm /tmp/spotless-result
    echo "✅ Spotless formatting applied successfully.\n"
}

# Run Dependency Guard
run_dependency_guard() {
    echo "\n📦 Generating dependency baseline...\n"
    ./gradlew dependencyGuardBaseline > /tmp/dependency-result
    if [ $? -ne 0 ]; then
        cat /tmp/dependency-result
        rm /tmp/dependency-result
        echo "\n❌ Failed to generate dependency baseline.\n"
        exit 1
    fi
    rm /tmp/dependency-result
    echo "✅ Dependency baseline generated successfully.\n"
}

# Run Detekt
run_detekt_checks() {
    echo "\n🔎 Running Detekt..."
    ./gradlew detekt > /tmp/detekt-result
    if [ $? -ne 0 ]; then
        cat /tmp/detekt-result
        rm /tmp/detekt-result
        echo "\n❌ Detekt found issues. Please review and fix them.\n"
        exit 1
    fi
    rm /tmp/detekt-result
    echo "✅ Detekt check passed."
}

# Success message
print_success_message() {
    GIT_USERNAME=$(git config user.name)
    echo "\n🎉 All checks passed. Great job, $GIT_USERNAME!\n"
    echo "🚀 Ready to commit and push your code."
}

# Main
check_current_branch
run_spotless_checks
run_detekt_checks
run_dependency_guard
print_success_message

exit 0
