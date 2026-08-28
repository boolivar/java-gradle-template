plugins {
    id("pl.allegro.tech.build.axion-release") version "1.21.1"
}

scmVersion {
    tag.prefix = ""
    useHighestVersion = true
    releaseOnlyOnReleaseBranches = true
    versionIncrementer("incrementMinorIfNotOnRelease", mapOf(
            "releaseBranchPattern" to "(?>release|(?>hot)?fix)/.+"
        )
    )
    branchVersionCreator = mapOf(
        "master" to "simple",
        "release/.*" to "simple",
        ".*" to "versionWithBranch"
    )
}

group = "org.bool.java"
version = scmVersion.version

subprojects {
    group = rootProject.group
    version = rootProject.version
}
