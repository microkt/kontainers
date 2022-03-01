package io.microkt.kontainers.docker.runner

/**
 * Return the current OS. Docker Desktop is unable to expose ports to an interface
 * other than localhost per [Docker Docs](https://docs.docker.com/desktop/mac/networking/).
 */
internal fun isOsLinux(): Boolean =
    System.getProperty("os.name").lowercase().contains("linux")
