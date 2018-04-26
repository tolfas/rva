package com.risevision.common.client.json;

import java.util.ArrayList;
import java.util.Date;

import com.risevision.common.client.info.TimeLineInfo;
import com.risevision.common.client.utils.RiseUtils;
import com.risevision.core.api.attributes.ScheduleItemAttribute;
import com.risevision.core.api.utils.DayOfWeek;

public class TimelineJsonParser {
	
	@SuppressWarnings("unused")
	private static TimeLineInfo parseTimeline(String jsonString) {	
		try {		
			if (jsonString != null && !jsonString.isEmpty()) {
				JSOModel jsoModel = JSOModel.fromJson(jsonString);
				
				return parseTimeline(jsoModel);
			}
		}
		catch (Exception e) {
			return null;
		}

		return null;
	}
	
	/*
	 * new implementation that never stuck through.
	public static TimeLineInfo parseTimeline(JSOModel jsItem) {
		try {
			if (jsItem.hasKey("timeline")) {
				return parseTimelineObject(jsItem.getObject("timeline"));
			}
			else {
				return parseTimelineFields(jsItem);
			}
		}
		catch (Exception e) {
			return null;
		}		
	}
	
	private static TimeLineInfo parseTimelineObject(JSOModel jsItem) {
		TimeLineInfo t = new TimeLineInfo();
		if (jsItem != null) {
			try {
				t.setUseSchedule(true);
				
				if (jsItem.hasKey("start")) {
					JSOModel jsStart = jsItem.getObject("start");
					t.setStartDate(RiseUtils.stringToDate(jsStart.get("date")));
					t.setStartTime(RiseUtils.stringToDate(jsStart.get("time")));
				}
				
				if (jsItem.hasKey("end")) {
					JSOModel jsEnd = jsItem.getObject("end");
					t.setEndDate(RiseUtils.stringToDate(jsEnd.get("date")));
					t.setEndTime(RiseUtils.stringToDate(jsEnd.get("time")));					
				}
				
				t.setNoEndDate(t.getEndDate() == null);
				t.setAllDay(t.getStartTime() == null);

				if (jsItem.hasKey("recurrence")) {
					JSOModel jsRecurrence = jsItem.getObject("recurrence");
					
					t.setRecurrenceType(parseRecurrenceType(jsItem));
					t.setRecurrenceFrequency(jsRecurrence.getInt(PlaylistAttribute.RECURRENCE_FREQUENCY, 1));
					t.setRecurrenceIsAbsolute(jsRecurrence.getBoolean(PlaylistAttribute.RECURRENCE_ABSOLUTE, true));
		        	t.setRecurrenceDayOfWeek(jsRecurrence.getInt(PlaylistAttribute.RECURRENCE_DAY_OF_WEEK, 0));
					t.setRecurrenceDayOfMonth(jsRecurrence.getInt(PlaylistAttribute.RECURRENCE_DAY_OF_MONTH, 1));
					t.setRecurrenceWeekOfMonth(jsRecurrence.getInt(PlaylistAttribute.RECURRENCE_WEEK_OF_MONTH, 0));
					t.setRecurrenceMonthOfYear(jsRecurrence.getInt(PlaylistAttribute.RECURRENCE_MONTH_OF_YEAR, 0));

					if (RecurrenceType.Weekly.equals(t.getRecurrenceType())) {
						String wd = jsRecurrence.get(PlaylistAttribute.RECURRENCE_DAYS_OF_WEEK);
						wd = (wd == null) ? "" : wd;
						t.setRecurrenceSunday(wd.contains(DayOfWeek.SUNDAY));
						t.setRecurrenceMonday(wd.contains(DayOfWeek.MONDAY));
						t.setRecurrenceTuesday(wd.contains(DayOfWeek.TUESDAY));
						t.setRecurrenceWednesday(wd.contains(DayOfWeek.WEDNESDAY));
						t.setRecurrenceThursday(wd.contains(DayOfWeek.THURSDAY));
						t.setRecurrenceFriday(wd.contains(DayOfWeek.FRIDAY));
						t.setRecurrenceSaturday(wd.contains(DayOfWeek.SATURDAY));
					}
				}

			}
			catch (Exception e) {
				
			}
		}
		
		return t;
	}
	*/
	
	public static TimeLineInfo parseTimeline(JSOModel jsItem) {
		TimeLineInfo t = new TimeLineInfo();
		try {
			t.setUseSchedule(jsItem.getBoolean(ScheduleItemAttribute.TIME_DEFINED, false));

			t.setStartDate(RiseUtils.stringToDate(jsItem.get(ScheduleItemAttribute.START_DATE)));
			t.setEndDate(RiseUtils.stringToDate(jsItem.get(ScheduleItemAttribute.END_DATE)));
			t.setNoEndDate(t.getEndDate() == null);

			t.setStartTime(RiseUtils.stringToDate(jsItem.get(ScheduleItemAttribute.START_TIME)));
			t.setEndTime(RiseUtils.stringToDate(jsItem.get(ScheduleItemAttribute.END_TIME)));
			t.setAllDay(t.getStartTime() == null);

			t.setRecurrenceType(parseRecurrenceType(jsItem));
			t.setRecurrenceFrequency(jsItem.getInt(ScheduleItemAttribute.RECURRENCE_FREQUENCY, 1));
			t.setRecurrenceIsAbsolute(jsItem.getBoolean(ScheduleItemAttribute.RECURRENCE_ABSOLUTE, true));
        	t.setRecurrenceDayOfWeek(jsItem.getInt(ScheduleItemAttribute.RECURRENCE_DAY_OF_WEEK, 0));
			t.setRecurrenceDayOfMonth(jsItem.getInt(ScheduleItemAttribute.RECURRENCE_DAY_OF_MONTH, 1));
			t.setRecurrenceWeekOfMonth(jsItem.getInt(ScheduleItemAttribute.RECURRENCE_WEEK_OF_MONTH, 0));
			t.setRecurrenceMonthOfYear(jsItem.getInt(ScheduleItemAttribute.RECURRENCE_MONTH_OF_YEAR, 0));

			if (TimeLineInfo.RecurrenceType.Weekly.equals(t.getRecurrenceType())) {
				String wd = jsItem.get(ScheduleItemAttribute.RECURRENCE_DAYS_OF_WEEK);
				wd = (wd == null) ? "" : wd;
				t.setRecurrenceSunday(wd.contains(DayOfWeek.SUNDAY));
				t.setRecurrenceMonday(wd.contains(DayOfWeek.MONDAY));
				t.setRecurrenceTuesday(wd.contains(DayOfWeek.TUESDAY));
				t.setRecurrenceWednesday(wd.contains(DayOfWeek.WEDNESDAY));
				t.setRecurrenceThursday(wd.contains(DayOfWeek.THURSDAY));
				t.setRecurrenceFriday(wd.contains(DayOfWeek.FRIDAY));
				t.setRecurrenceSaturday(wd.contains(DayOfWeek.SATURDAY));
			}

		} catch (Exception e) {
		}
		return t;
	}
	
	private static TimeLineInfo.RecurrenceType parseRecurrenceType(JSOModel jsItem) {
		TimeLineInfo.RecurrenceType res = TimeLineInfo.RecurrenceType.Daily;
		String s = jsItem.get(ScheduleItemAttribute.RECURRENCE_TYPE);
		if ("week".equals(s) || TimeLineInfo.RecurrenceType.Weekly.toString().equals(s))
			res = TimeLineInfo.RecurrenceType.Weekly;
		else if ("month".equals(s) || TimeLineInfo.RecurrenceType.Monthly.toString().equals(s))
			res = TimeLineInfo.RecurrenceType.Monthly;
		else if ("year".equals(s) || TimeLineInfo.RecurrenceType.Yearly.toString().equals(s))
			res = TimeLineInfo.RecurrenceType.Yearly;
		return res;
	}
	
	@SuppressWarnings("unused")
	private static String updateTimeline(TimeLineInfo timeline) {
		try {		
			JSOModel jsTimeline = JSOModel.create();
			
			updateTimeline(jsTimeline, timeline);
			
			return jsTimeline.getAsString();
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public static boolean updateTimeline(JSOModel jsItem, TimeLineInfo t) {				
		try {
			if (t == null) {
				jsItem.set(ScheduleItemAttribute.TIME_DEFINED, false);
				return false;
			}
			
			jsItem.set(ScheduleItemAttribute.TIME_DEFINED, t.getUseSchedule());

			jsItem.set(ScheduleItemAttribute.START_DATE, t.getStartDate());
			if (!t.getNoEndDate())
				jsItem.set(ScheduleItemAttribute.END_DATE, t.getEndDate());
			else
				jsItem.set(ScheduleItemAttribute.END_DATE, (Date) null);

			if (!t.getAllDay()) {
				jsItem.set(ScheduleItemAttribute.START_TIME, t.getStartTime());
				jsItem.set(ScheduleItemAttribute.END_TIME, t.getEndTime());
			}
			else {
				jsItem.set(ScheduleItemAttribute.START_TIME, (Date) null);
				jsItem.set(ScheduleItemAttribute.END_TIME, (Date) null);
			}

			jsItem.set(ScheduleItemAttribute.RECURRENCE_TYPE, t.getRecurrenceType().toString());
			
			jsItem.set(ScheduleItemAttribute.RECURRENCE_FREQUENCY, t.getRecurrenceFrequency());
			jsItem.set(ScheduleItemAttribute.RECURRENCE_ABSOLUTE, t.getRecurrenceIsAbsolute());
        	jsItem.set(ScheduleItemAttribute.RECURRENCE_DAY_OF_WEEK, t.getRecurrenceDayOfWeek());
			jsItem.set(ScheduleItemAttribute.RECURRENCE_DAY_OF_MONTH, t.getRecurrenceDayOfMonth());
			jsItem.set(ScheduleItemAttribute.RECURRENCE_WEEK_OF_MONTH, t.getRecurrenceWeekOfMonth());
			jsItem.set(ScheduleItemAttribute.RECURRENCE_MONTH_OF_YEAR, t.getRecurrenceMonthOfYear());

			if (TimeLineInfo.RecurrenceType.Weekly.equals(t.getRecurrenceType())) {	
				ArrayList<String> daysOfWeek = new ArrayList<String>();
				
				if (t.getRecurrenceSunday()) daysOfWeek.add(DayOfWeek.SUNDAY);
				if (t.getRecurrenceMonday()) daysOfWeek.add(DayOfWeek.MONDAY);
				if (t.getRecurrenceTuesday()) daysOfWeek.add(DayOfWeek.TUESDAY);
				if (t.getRecurrenceWednesday()) daysOfWeek.add(DayOfWeek.WEDNESDAY);
				if (t.getRecurrenceThursday()) daysOfWeek.add(DayOfWeek.THURSDAY);
				if (t.getRecurrenceFriday()) daysOfWeek.add(DayOfWeek.FRIDAY);
				if (t.getRecurrenceSaturday()) daysOfWeek.add(DayOfWeek.SATURDAY);
				
				jsItem.set(ScheduleItemAttribute.RECURRENCE_DAYS_OF_WEEK, daysOfWeek);
			}

		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
}
