// Unique 3-Digit Even Numbers
// https://leetcode.com/problems/unique-3-digit-even-numbers/

class Solution {
    public int totalNumbers(int[] digits) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < digits.length; i++) {

            // No leading zero
            if (digits[i] == 0) continue;

            for (int j = 0; j < digits.length; j++) {

                // Cannot use the same copy
                if (j == i) continue;

                for (int k = 0; k < digits.length; k++) {

                    // Cannot use the same copy
                    if (k == i || k == j) continue;

                    // Last digit must be even
                    if (digits[k] % 2 != 0) continue;

                    int number = digits[i] * 100
                               + digits[j] * 10
                               + digits[k];

                    set.add(number);
                }
            }
        }

        return set.size();
    }
}
