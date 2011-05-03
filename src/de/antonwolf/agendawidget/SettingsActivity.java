package de.antonwolf.agendawidget;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity {
	public final static String EXTRA_WIDGET_ID = "widgetId";
	static final String TAG = "AgendaWidget";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		int widgetId = getIntent().getIntExtra(EXTRA_WIDGET_ID, -1);
		Log.d(TAG, "SettingsActivity.onCreate(" + widgetId + ")");
		if (-1 == widgetId)
			return;
		WidgetPreferences prefs = new WidgetPreferences(widgetId, this);

		PreferenceScreen screen = getPreferenceManager()
				.createPreferenceScreen(this);
		setPreferenceScreen(screen);

		PreferenceCategory display = new PreferenceCategory(this);
		display.setTitle(R.string.settings_display);
		screen.addPreference(display);

		ListPreference lines = new ListPreference(this);
		lines.setTitle(R.string.settings_display_lines);
		lines.setKey(prefs.getLinesKey());
		lines.setSummary(R.string.settings_display_lines_summary);
		lines.setEntries(R.array.settings_display_lines_entries);
		lines.setEntryValues(new String[] { "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22", "23", "24", "25" });
		lines.setDefaultValue(Integer.toString(prefs.getLines()));
		display.addPreference(lines);

		ListPreference birthdays = new ListPreference(this);
		birthdays.setTitle(R.string.settings_birthdays);
		birthdays.setKey(prefs.getBirthdaysKey());
		birthdays.setSummary(R.string.settings_birthdays_summary);
		birthdays.setEntries(R.array.settings_birthdays_entries);
		birthdays.setEntryValues(new String[] { WidgetPreferences.BIRTHDAY_SPECIAL,
				WidgetPreferences.BIRTHDAY_NORMAL,
				WidgetPreferences.BIRTHDAY_HIDE });
		birthdays.setDefaultValue(WidgetPreferences.BIRTHDAY_SPECIAL);
		display.addPreference(birthdays);
		
		CheckBoxPreference weekday = new CheckBoxPreference(this);
		weekday.setDefaultValue(prefs.isWeekday());
		weekday.setKey(prefs.getWeekdayKey());
		weekday.setTitle(R.string.settings_weekday);
		weekday.setSummaryOn(R.string.settings_weekday_yes);
		weekday.setSummaryOff(R.string.settings_weekday_no);
		display.addPreference(weekday);
		
		CheckBoxPreference tomorrowYesterday = new CheckBoxPreference(this);
		tomorrowYesterday.setDefaultValue(prefs.isTommorowYesterday());
		tomorrowYesterday.setKey(prefs.getTommorowYesterdayKey());
		tomorrowYesterday.setTitle(R.string.settings_tommorow_yesterday);
		tomorrowYesterday.setSummaryOn(R.string.settings_tommorow_yesterday_yes);
		tomorrowYesterday.setSummaryOff(R.string.settings_tommorow_yesterday_no);
		display.addPreference(tomorrowYesterday);
		
		CheckBoxPreference calendarColor = new CheckBoxPreference(this);
		calendarColor.setDefaultValue(prefs.isCalendarColor());
		calendarColor.setKey(prefs.getCalendarColorKey());
		calendarColor.setTitle(R.string.settings_calendar_color);
		calendarColor.setSummaryOn(R.string.settings_calendar_color_show);
		calendarColor.setSummaryOff(R.string.settings_calendar_color_hide);
		display.addPreference(calendarColor);

		PreferenceCategory calendars = new PreferenceCategory(this);
		calendars.setTitle(R.string.settings_calendars);
		screen.addPreference(calendars);

		for (WidgetPreferences.CalendarPreferences cprefs : prefs
				.getCalendars()) {
			CheckBoxPreference calendar = new CheckBoxPreference(this);
			calendar.setDefaultValue(prefs.isCalendar(cprefs.calendarId));
			calendar.setKey(prefs.getCalendarKey(cprefs.calendarId));
			calendar.setSummaryOn(R.string.settings_calendars_show);
			calendar.setSummaryOff(R.string.settings_calendars_hide);
			calendar.setTitle(cprefs.displayName);
			calendars.addPreference(calendar);
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		int widgetId = getIntent().getIntExtra(EXTRA_WIDGET_ID, -1);
		Log.d(TAG, "SettingsActivity.onResume(" + widgetId + ")");
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		int widgetId = getIntent().getIntExtra(EXTRA_WIDGET_ID, -1);
		Log.d(TAG, "SettingsActivity.onPause(" + widgetId + ")");
		if (-1 == widgetId)
			return;

		Intent intent = new Intent("update", Uri.parse("widget://" + widgetId),
				this, WidgetService.class);
		Log.d(TAG, "Sending " + intent);
		startService(intent);
	}
}
