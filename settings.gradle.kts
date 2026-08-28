import java.nio.file.FileVisitResult
import kotlin.io.path.name
import kotlin.io.path.visitFileTree

rootDir.toPath().visitFileTree {
    onPreVisitDirectory { directory, _ ->
        if (directory.name == "buildSrc" || directory.name.startsWith(".")) {
            FileVisitResult.SKIP_SUBTREE
        } else {
            FileVisitResult.CONTINUE
        }
    }
    onVisitFile { file, _ ->
        if (file.name == "build.gradle" || file.name == "build.gradle.kts") {
            include(rootDir.toPath().relativize(file.parent).joinToString(":"))
        }
        FileVisitResult.CONTINUE
    }
}