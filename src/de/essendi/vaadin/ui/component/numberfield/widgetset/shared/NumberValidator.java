/* 
 * Copyright 2012 essendi it GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package de.essendi.vaadin.ui.component.numberfield.widgetset.shared;

import static de.essendi.vaadin.ui.component.numberfield.widgetset.shared.Constants.NEGATIVE_PREFIX;

/**
 * <p>
 * <code>NumberValidator</code> provides static methods to ensure that a string
 * represents a valid number.
 * </p>
 * <p>
 * If you change this class, don't forget to recompile the client-side widgetset
 * code ({@link VNumberField}).
 * </p>
 */
public abstract class NumberValidator {

	/**
	 * Checks whether a string represents a valid decimal number.
	 * 
	 * @param toCheck
	 *            The string to check.
	 * @param attr
	 *            The validity is checked on the basis of the settings in the
	 *            given {@link NumberFieldAttributes} object.
	 * @return True, if the given string represents a valid decimal number,
	 *         otherwise false.
	 */
	public static boolean isValidDecimal(String toCheck,
			NumberFieldAttributes attr) {
		String groupingSeparator = attr.isGroupingUsed() ? attr
				.getEscapedGroupingSeparator() : "";

		// Produce a regular expression similar to this: [0-9\.]*,?[0-9\.]{0,2}
		String regExp = patternForNegatives(attr.isNegativeAllowed()) + "[0-9"
				+ groupingSeparator + "]*" + attr.getEscapedDecimalSeparator()
				+ "?[0-9" + groupingSeparator + "]{0,"
				+ attr.getDecimalPrecision() + "}";

		return isWithinBoundsAndMatchesRegExp(toCheck, regExp, attr);
	}

	/**
	 * Checks whether a string represents a valid integer number.
	 * 
	 * @param toCheck
	 *            The string to check.
	 * @param attr
	 *            The validity is checked on the basis of the settings in the
	 *            given {@link NumberFieldAttributes} object.
	 * @return True, if the given string represents a valid integer number,
	 *         otherwise false.
	 */
	public static boolean isValidInteger(String toCheck,
			NumberFieldAttributes attr) {
		String groupingSeparator = attr.isGroupingUsed() ? attr
				.getEscapedGroupingSeparator() : "";

		// Produce a regular expression similar to this: [0-9\.]*
		String regExp = patternForNegatives(attr.isNegativeAllowed()) + "[0-9"
				+ groupingSeparator + "]*";

		return isWithinBoundsAndMatchesRegExp(toCheck, regExp, attr);
	}

	private static String patternForNegatives(boolean allowNegative) {
		return allowNegative ? NEGATIVE_PREFIX + "?" : "";
	}

	private static boolean isWithinBoundsAndMatchesRegExp(String toCheck,
			String regExp, NumberFieldAttributes attr) {
		// "-" -> "-0"
		if (NEGATIVE_PREFIX.equals(toCheck)) {
			toCheck = NEGATIVE_PREFIX + "0";
		}

		// 2.546,99 -> 2546.99
		String groupingSeparator = String.valueOf(attr.getGroupingSeparator());
		String toCheckNonLocalized = toCheck.replace(groupingSeparator, "")
				.replace(attr.getDecimalSeparator(), '.');

		double value;
		try {
			value = Double.valueOf(toCheckNonLocalized);
		} catch (NumberFormatException e) {
			return false;
		}

		boolean valueIsWithinBounds = value >= attr.getMinValue()
				&& value <= attr.getMaxValue();
		boolean valueMatchesRegExp = toCheck.matches(regExp);
		if (!valueIsWithinBounds || !valueMatchesRegExp) {
			return false;
		}

		return true;
	}

}
