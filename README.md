# Bombamod — Fabric 1.21.4

Prosty, schludny mod klientowy do Minecraft Java 1.21.4.

## Wymagania
- Minecraft Java Edition 1.21.4
- Fabric Loader 0.16.10 lub nowszy zgodny z 1.21.4
- Fabric API 0.119.2+1.21.4
- Java 21

## Sterowanie
- **F6** — otwiera panel Bombamod.
- W panelu wybierz **Skanuj latest.log**, aby policzyć poprawne składniowo adresy IPv4 zapisane w lokalnym `logs/latest.log`.

## Co robi skaner
- Czyta wyłącznie plik `logs/latest.log` z folderu uruchomieniowego Minecrafta.
- Pokazuje liczbę wystąpień adresów IPv4; nie wyświetla samych adresów.
- Nie wysyła danych do sieci, nie wykonuje geolokalizacji i nie przypisuje adresów do graczy.
- Wynik jest tylko wskazówką: samo wystąpienie IPv4 nie dowodzi wycieku.

## Czego mod nie robi
- Nie wyrzuca graczy z serwera i nie wysyła pakietów zakłócających działanie serwera.
- Nie odczytuje prywatnych adresów IP innych graczy.
- Nie wymaga OP, permisji ani komend administracyjnych.
- Nie omija zabezpieczeń serwera.

## Budowanie JAR-a przez GitHub Actions
W projekcie jest gotowy workflow `.github/workflows/build.yml`.

1. Zaloguj się na GitHub i utwórz nowe, puste repozytorium.
2. Rozpakuj ZIP projektu i wgraj **całą zawartość** folderu do repozytorium (tak, aby `build.gradle` i folder `.github` były w głównym katalogu).
3. Zapisz zmiany w gałęzi `main`.
4. Otwórz zakładkę **Actions** w repozytorium.
5. Wybierz **Build Bombamod**. Workflow uruchomi się po pushu; możesz też wybrać **Run workflow**.
6. Po zakończeniu otwórz wykonany workflow i pobierz artefakt `bombamod-fabric-1.21.4`.
7. W ZIP-ie artefaktu znajdziesz `bombamod-1.0.0.jar`.

## Instalacja
Umieść `bombamod-1.0.0.jar` w folderze `.minecraft/mods`. Upewnij się, że masz Fabric Loader i Fabric API dla Minecraft 1.21.4.
