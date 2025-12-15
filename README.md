# Socket Echo System — Kurz (DE) / Short (EN)

Deutsch (kurz)
--------------
Projekt enthält zwei Klassen: `EchoServer` und `EchoClient` (Paket: `EchoSystem`).

Worum es geht
- `EchoServer` lauscht auf einem TCP-Port, empfängt Nachrichten, antwortet mit derselben Nachricht und speichert Chat-Zeilen in `server.log`.
- Unterstützt speziellen Befehl: `/cmd wget <URL>` — führt eine HTTP-GET-Anfrage aus und sendet eine Kurz-Zusammenfassung (Status, Länge, Bytes) zurück.
- `EchoServer` liest `exitCommand` aus `config.properties` (Standard: `exit`) und erwartet die Umgebungsvariable `PORT`.
- `EchoClient` verbindet sich mit `localhost` am Port aus `PORT`, sendet Zeilen von der Konsole und zeigt Server-Antworten an. Eingabe `exit` beendet die Sitzung.

Wichtig
- Benötigt Java 11+ (HTTP Client API).
- Dateien: `src/EchoSystem/EchoServer.java` und `src/EchoSystem/EchoClient.java`.
- Aktuelle main-Signaturen sind `static void main(String[] args)` (paket-privat). Für zuverlässiges Starten via `java` empfiehlt sich `public static void main(String[] args)`.

Kompilieren & Start (Beispiel)
```bash
# Kompilieren
javac -d out src/EchoSystem/*.java

# Server starten (zuerst) — PORT setzen
export PORT=12345
java -cp out EchoSystem.EchoServer

# Client starten (in neuer Shell)
export PORT=12345
java -cp out EchoSystem.EchoClient
```

Hinweise
- Fehlt `PORT` oder ist `config.properties` nicht vorhanden, können Fehler auftreten.
- Logdatei: `server.log` (append-Modus). Für Produktion: Robustere Fehlerbehandlung, Timeouts, Input-Validierung.

English (short)
---------------
This project contains two classes: `EchoServer` and `EchoClient` (package `EchoSystem`).

What it does
- `EchoServer` listens on a TCP port, echoes received messages back, and appends chat lines to `server.log`.
- Special command: `/cmd wget <URL>` — performs an HTTP GET and returns a short result (status, length, bytes).
- `EchoServer` reads `exitCommand` from `config.properties` (default `exit`) and expects the `PORT` environment variable.
- `EchoClient` connects to `localhost` at `PORT`, sends console lines, and prints server responses. Type `exit` to close.

Requirements & notes
- Java 11+ (uses java.net.http.HttpClient).
- Files: `src/EchoSystem/EchoServer.java`, `src/EchoSystem/EchoClient.java`.
- Consider changing `static void main(...)` to `public static void main(...)` for standard launcher compatibility.

Build & run (example)
```bash
# Build
javac -d out src/EchoSystem/*.java

# Start server (first)
export PORT=12345
java -cp out EchoSystem.EchoServer

# Start client (new shell)
export PORT=12345
java -cp out EchoSystem.EchoClient
```

Notes
- Missing `PORT` or `config.properties` will cause runtime errors.
- `server.log` grows by append; secure and rotate logs for long-term use.
