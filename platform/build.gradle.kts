plugins {
    id("java-platform")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(platform(libs.spring.boot.dependencies))
    constraints {
        api(libs.commons.io)
        api(libs.commons.collections)
    }
}
