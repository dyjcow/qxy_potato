package com.qxy.potatos.util;

import com.qxy.potatos.util.AI.tflite.OperatingHandClassifier;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueUtil {
	private static final ArrayBlockingQueue<Integer> handList = new ArrayBlockingQueue<>(3);
	private static int handLeft = 0;
	private static int handRight = 0;

	public static int getRecentHand(int labelHand) {
		int poll;
		if (!handList.offer(labelHand)) {
			poll = handList.remove();
			handList.offer(labelHand);
			if (poll == labelHand) {
				return compareRecentHand(handLeft, handRight);
			} else {
				switch (poll) {
				case OperatingHandClassifier.labelRight:
					handRight--;
					break;
				case OperatingHandClassifier.labelLeft:
					handLeft--;
					break;
				default:
					break;
				}
			}
		}

		switch (labelHand) {
		case OperatingHandClassifier.labelRight:
			handRight++;
			break;
		case OperatingHandClassifier.labelLeft:
			handLeft++;
			break;
		default:
			break;
		}
		return compareRecentHand(handLeft, handRight);
	}

	private static int compareRecentHand(int handLeft, int handRight) {
		if (handLeft > handRight) {
			return OperatingHandClassifier.labelLeft;
		} else {
			return OperatingHandClassifier.labelRight;
		}
	}
}