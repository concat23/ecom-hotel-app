package dev.concat.vab.ecomhotelappbackend.utils;

public class GenerateCustomize {
    private static int currentDigit = 0;
    private static char currentLetter = 'A';
    private static String generateRoomCode() {
        // Ensure the current letter is within 'A' and 'Z'
        if (currentLetter > 'Z') {
            currentLetter = 'A';
        }

        // Ensure the current digit is within 0 and 9
        if (currentDigit > 9) {
            currentDigit = 0;
        }

        // Create the room code by concatenating the characters and numbers with a hyphen
        String roomCode = String.format("%c%c-%02d", currentLetter, ++currentLetter, currentDigit++);

        return roomCode;
    }
}
