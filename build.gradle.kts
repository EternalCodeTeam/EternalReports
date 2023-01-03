import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "2.0.1"
}

group = "com.eternalcode"
version = "1.0.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
    maven { url = uri("https://maven.enginehub.org/repo") }
}

dependencies {
    // SpigotAPI
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    // Kyori Adventure
    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit-adventure:2.7.0")

    // Cdn
    implementation("net.dzikoysk:cdn:1.14.2")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // io.github.mineflash07/discord-webhook
    implementation("io.github.mineflash07:discord-webhook:1.0.1")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

bukkit {
    main = "com.eternalcode.reports.EternalReports"
    apiVersion = "1.13"
    prefix = "EternalReports"
    author = "EternalCode"
    name = "EternalReports"
    description = "A simple plugin for send report to administration"
    version = "${project.version}"
}


java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    runServer {
        minecraftVersion("1.18.2")
    }
}

tasks.withType<ShadowJar> {
    archiveFileName.set("EternalReports v${project.version} (1.13+).jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**"
    )

    mergeServiceFiles()
    minimize()

    val prefix = "com.eternalcode.reports.libs"

    listOf(
        "panda",
        "org.panda_lang",
        "org.bstats",
        "net.dzikoysk",
        "net.kyori",
        "dev.rollczi"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}
