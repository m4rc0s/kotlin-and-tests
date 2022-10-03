import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.mooltiverse.oss.nyx") version "1.2.1"
    id("com.github.node-gradle.node") version "3.4.0"
}

repositories {
    mavenCentral()
}

tasks.register<NpxTask>("createRelease") {
    command.apply { set("semantic-release") }
    args.set(listOf(
        "--no-ci",
        "--dry-run",
        "false",
        "-p",
        "\"@semantic-release/commit-analyzer, @semantic-release/release-notes-generator, @semantic-release/github\"",
        "--branches",
        "\"main\"")
    )
}

nyx {
    commitMessageConventions.enabled.add("conventionalCommits")
    preset.apply { set("extended") }
    releaseTypes {
        releasePrefix.set("v")
        git {
            remotes {
                add(remotes.create("origin")
                    .also {
                        it.user.set(System.getenv("GITHUB_TOKEN"))
                        it.password.set(" ")
                    }
                )
            }
        }
        enabled.add("mainline")
        dryRun.apply { set(false) }
        publicationServices.apply {
            add("github")
        }
        remoteRepositories.apply {
            add("origin")
        }
        items {
            add(create("mainline").also {
                it.collapseVersions.apply { set(false) }
                it.versionRangeFromBranchName.apply { set(false) }
                it.gitCommit.apply { set("false") }
                it.matchBranches.apply { set("^(main)$") }
                it.publish.apply { set("true") }
                it.gitTag.apply { set("true") }
                it.gitCommitMessage.apply { set("Testing commit message format") }
                it.description.apply { set("{{#file.content}}CHANGELOG.md{{/file.content}}") }
            })
        }
    }
    scheme.apply { set("SEMVER") }
    services {
        add(services.create("github").also {
            it.type.apply { set("GITHUB") }
            it.options {
                set(
                    mapOf(
                        "AUTHENTICATION_TOKEN" to System.getenv("GITHUB_TOKEN"),
                        "REPOSITORY_NAME" to "quality-and-delivery-pipelines",
                        "REPOSITORY_OWNER" to "m4rc0s"
                    )
                )
            }
        })
    }
}