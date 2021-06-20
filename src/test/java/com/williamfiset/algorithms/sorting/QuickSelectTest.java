package com.williamfiset.algorithms.sorting;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class QuickSelectTest {

    @Test
    public void testQuickSelect() {
        for (int size = 1; size < 500; size++) {
            // Given
            QuickSelect quickSelect = new QuickSelect();
            int[] values = TestUtils.randomIntegerArray(size, -100, 100);

            for (int k = 1; k <= size; k++) {
                int[] copy = values.clone();
                Arrays.sort(values);

                // When
                int kthLargestElement = quickSelect.quickSelect(copy, k);

                // Then
                assertThat(kthLargestElement).isEqualTo(values[k - 1]);
            }
        }
    }
}
