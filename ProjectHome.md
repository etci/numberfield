# `NumberField` Vaadin add-on #

## What it is ##

`NumberField` provides a numeric text field with automatic keystroke filtering and validation for integer (123) and decimal numbers (12.3). The minus sign and user-definable grouping and decimal separators are supported.

Inputs are validated on client- <b>and</b> server-side. The client-side validator gets active on every keypress in the field. If the keypress would lead to an invalid value, it is suppressed and the value remains unchanged. The server-side validation is triggered when the field loses focus.

An user-entered value is formatted automatically when the field's focus is lost. `NumberField` uses [DecimalFormat](http://docs.oracle.com/javase/6/docs/api/java/text/DecimalFormat.html) for formatting and send the formatted value of the input back to client. There's a number of setters to define the format, see the code example below for a general view.

## Installing ##

Information how to use the Vaadin add-on in your project is here: http://vaadin.com/directory/help/using-vaadin-add-ons (section "Installing Add-ons").

This add-on is also available from http://vaadin.com/addon/numberfield.

## Sample code ##

Some features in an usage example:
```
NumberField numField = new NumberField();        // NumberField extends TextField
numField.setDecimalAllowed(true);                // not just integers (by default, decimals are allowed)
numField.setDecimalPrecision(2);                 // maximum 2 digits after the decimal separator
numField.setDecimalSeparator(',');               // e.g. 1,5
numField.setDecimalSeparatorAlwaysShown(true);   // e.g. 12345 -> 12345,
numField.setMinimumFractionDigits(2);            // e.g. 123,4 -> 123,40
numField.setGroupingUsed(true);                  // use grouping (e.g. 12345 -> 12.345)
numField.setGroupingSeparator('.');              // use '.' as grouping separator
numField.setGroupingSize(3);                     // 3 digits between grouping separators: 12.345.678
numField.setMinValue(0);                         // valid values must be >= 0 ...
numField.setMaxValue(999.9);                     // ... and <= 999.9
numField.setErrorText("Invalid number format!"); // feedback message on bad input
numField.setNegativeAllowed(false);              // prevent negative numbers (defaults to true)
numField.setValueIgnoreReadOnly("10");           // set the field's value, regardless whether it is read-only or not
numField.removeValidator();                      // omit server-side validation
```