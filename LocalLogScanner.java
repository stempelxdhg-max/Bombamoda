package pl.bombamod;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class LocalLogScanner {
    private static final long MAX_LOG_BYTES = 25L * 1024L * 1024L;
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "(?<![0-9])([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})(?![0-9])"
    );

    private LocalLogScanner() {
    }

    public record ScanResult(boolean success, String message) {
    }

    /**
     * Counts IPv4-shaped values in the local latest.log. Values are never
     * displayed, transmitted, resolved, or associated with player identities.
     * This is a privacy hint, not proof that a value is a real IP leak.
     */
    public static ScanResult scanLatestLog() {
        Path logPath = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("logs")
                .resolve("latest.log");

        if (!Files.isRegularFile(logPath)) {
            return new ScanResult(false, Text.translatable("screen.bombamod.no_log").getString());
        }

        try {
            long size = Files.size(logPath);
            if (size > MAX_LOG_BYTES) {
                return new ScanResult(false, Text.translatable("screen.bombamod.too_large").getString());
            }

            long count = 0;
            try (Stream<String> lines = Files.lines(logPath)) {
                for (var iterator = lines.iterator(); iterator.hasNext();) {
                    String line = iterator.next();
                    Matcher matcher = IPV4_PATTERN.matcher(line);
                    while (matcher.find()) {
                        if (isValidIpv4(matcher)) {
                            count++;
                        }
                    }
                }
            }

            String message = Text.translatable("screen.bombamod.result", count).getString();
            return new ScanResult(true, message);
        } catch (IOException | SecurityException exception) {
            return new ScanResult(false, Text.translatable("screen.bombamod.scan_error").getString());
        }
    }

    private static boolean isValidIpv4(Matcher matcher) {
        for (int group = 1; group <= 4; group++) {
            try {
                int octet = Integer.parseInt(matcher.group(group));
                if (octet < 0 || octet > 255) {
                    return false;
                }
            } catch (NumberFormatException exception) {
                return false;
            }
        }
        return true;
    }
}
