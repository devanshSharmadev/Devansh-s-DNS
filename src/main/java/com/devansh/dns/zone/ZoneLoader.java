package com.devansh.dns.zone;

import com.devansh.dns.protocol.RecordType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ZoneLoader {

    private static final String ZONE_FILE = "zone.db";

    public Zone load() {

        Zone zone = new Zone();

        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(ZONE_FILE);

        if (inputStream == null) {
            throw new IllegalStateException(
                    "Zone file not found: " + ZONE_FILE);
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     inputStream,
                                     StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                line = line.trim();

                // Ignore blank lines
                if (line.isEmpty()) {
                    continue;
                }

                // Ignore comments
                if (line.startsWith("#")) {
                    continue;
                }

                ZoneRecord record = parseRecord(line, lineNumber);

                zone.addRecord(record);
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read zone file.", e);
        }

        return zone;
    }

    private ZoneRecord parseRecord(String line,
                                   int lineNumber) {

        String[] tokens = line.split("\\s+");

        if (tokens.length != 4) {

            throw new IllegalArgumentException(
                    "Invalid zone record at line "
                            + lineNumber
                            + ": "
                            + line);
        }

        String domain = tokens[0];

        long ttl = Long.parseLong(tokens[1]);

        RecordType type = RecordType.valueOf(tokens[2]);

        String value = tokens[3];

        return new ZoneRecord(
                domain,
                type,
                value,
                ttl
        );
    }
}