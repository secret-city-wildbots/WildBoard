package frc.robot.WildBoard;

import edu.wpi.first.wpilibj.Filesystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FrontendBuilder {
    public static void buildFrontend() {
        File deployDir = Filesystem.getDeployDirectory();
        File frontendDir = new File(deployDir, "WildBoard/frontend");

        File esbuildSrc = new File(frontendDir, "src/esbuild");
        File esbuildTmp = new File("/tmp/esbuild");

        // Copy esbuild only if missing or file size differs
        try {
            boolean copy = true;
            if (esbuildTmp.exists() && esbuildTmp.length() == esbuildSrc.length()) {
                copy = false;
            }
            if (copy) {
                Files.copy(esbuildSrc.toPath(), esbuildTmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                esbuildTmp.setExecutable(true);
            }
        } catch (IOException e) {
            System.err.println("Failed to copy esbuild to /tmp: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Output directory in /tmp (writable)
        File outputDir = new File("/tmp/frontend-public");
        outputDir.mkdirs();

        // Build command using the /tmp copy
        ProcessBuilder pb = new ProcessBuilder(
            esbuildTmp.getAbsolutePath(),
            "./src/pages/indexLoader.tsx",
            "--bundle",
            "--outfile=" + new File(outputDir, "index.js").getAbsolutePath(),
            "--format=esm",
            "--platform=browser",
            "--jsx-factory=h",
            "--jsx-fragment=Fragment",
            "--jsx=transform",
            "--minify"
        );

        pb.directory(frontendDir);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            process.getInputStream().transferTo(System.out);
            int exitCode = process.waitFor();
            System.out.println("Frontend build complete (exit " + exitCode + ")");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}