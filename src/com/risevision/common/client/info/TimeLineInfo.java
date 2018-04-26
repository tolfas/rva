package com.risevision.common.client.info;

import java.io.Serializable;
import java.util.Date;

import com.risevision.common.client.utils.RiseUtils;

@SuppressWarnings("serial")
public class TimeLineInfo implements Serializable {

	private String SUNDAY = "Sun";
	private String MONDAY = "Mon";
	private String TUESDAY = "Tue";
	private String WEDNESDAY = "Wed";
	private String THURSDAY = "Thu";
	private String FRIDAY = "Fri";
	private String SATURDAY = "Sat";
	
	public static final String DEFAULT_DESCRIPTION = "";
	public enum RecurrenceType {Daily, Weekly, Monthly, Yearly};
	public static final String[] WEEKOFMONTHNAMES = {"First","Second","Third","Fourth","Last"};
	public static final String[] WEEKDAYNAMES = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	public static final String[] MONTHNAMES = {"January","February","March","April","May","June","July","August","September","October","November","December"};

//	private static int msecInOneDay = 1000 * 60 * 60 * 24;
		
	private boolean useSchedule = false; // false=24/7; true=scheduled
	private Date startDate = new Date();
	private Date endDate = new Date();
	private boolean noEndDate = true;
	
	private Date startTime = new Date();
	private Date endTime = new Date();
	private boolean allDay = true;
	
	private String startDateString, startTimeString, endDateString, endTimeString;

	private String timeZone = "0";
	private boolean useDisplayTime = true;

	private RecurrenceType recurrenceType = RecurrenceType.Daily;
	private int recurrenceFrequency = 1;

	private boolean recurrenceSunday;
	private boolean recurrenceMonday;
	private boolean recurrenceTuesday;
	private boolean recurrenceWednesday;
	private boolean recurrenceThursday;
	private boolean recurrenceFriday;
	private boolean recurrenceSaturday;

	private boolean recurrenceIsAbsolute = true;	

	private int recurrenceDayOfWeek;
	private int recurrenceDayOfMonth;
	private int recurrenceWeekOfMonth;
	private int recurrenceMonthOfYear;
	
	public TimeLineInfo() {
	}
	
	public void updateDatesToString() {
		startDateString = RiseUtils.dateToStringRfc822IgnoreTimezone(startDate);
		startTimeString = RiseUtils.dateToStringRfc822IgnoreTimezone(startTime);
		endDateString = RiseUtils.dateToStringRfc822IgnoreTimezone(endDate);
		endTimeString = RiseUtils.dateToStringRfc822IgnoreTimezone(endTime);
	}
	
	public void parseDatesFromString() {
		if (startDateString != null && !startDateString.isEmpty()) {
			startDate = RiseUtils.strToDateRfc822IgnoreTimezone(startDateString);
		}
		if (startTimeString != null && !startTimeString.isEmpty()) {
			startTime = RiseUtils.strToDateRfc822IgnoreTimezone(startTimeString);
		}
		if (endDateString != null && !endDateString.isEmpty()) {
			endDate = RiseUtils.strToDateRfc822IgnoreTimezone(endDateString);
		}
		if (endTimeString != null && !endTimeString.isEmpty()) {
			endTime = RiseUtils.strToDateRfc822IgnoreTimezone(endTimeString);
		}
	}

	public String getDescription() {
		return getDescription(useSchedule);
	}
	
	public String getDescription(boolean useSchedule) {
		String desc = DEFAULT_DESCRIPTION;
		if (useSchedule) {
			String sTime = "";
			if (!allDay)
				sTime = " " + RiseUtils.timeToString(startTime) + " to " + RiseUtils.timeToString(endTime);

			String sFrequency = Integer.toString(recurrenceFrequency);
			
			if (recurrenceType == RecurrenceType.Daily) {
				desc = "Daily" + sTime + " (Every " + sFrequency + " day(s))";
			} else if (recurrenceType == RecurrenceType.Weekly) {
				String wd = weekDaysToString();
				if (wd.length() > 0)
					wd = " (" + wd + ")";
				desc = "Weekly" + sTime + wd;
			} else if (recurrenceType == RecurrenceType.Monthly) {
				desc = "Monthly" + sTime;
                if (recurrenceIsAbsolute)
                	desc += " (Day " + Integer.toString(recurrenceDayOfMonth) + " of Every " + sFrequency + " Month(s))";
                else
                	desc += " (the " + WEEKOFMONTHNAMES[recurrenceWeekOfMonth] + " " + WEEKDAYNAMES[recurrenceDayOfWeek] + " of Every " + sFrequency + " Month(s))";
			} else if (recurrenceType == RecurrenceType.Yearly) {
				desc = "Yearly" + sTime;
                if (recurrenceIsAbsolute)
                    desc += " (" + MONTHNAMES[recurrenceMonthOfYear] + " " + Integer.toString(recurrenceDayOfMonth) + ")";
                else
                	desc += " (the " + WEEKOFMONTHNAMES[recurrenceWeekOfMonth] + " " + WEEKDAYNAMES[recurrenceDayOfWeek] + " of " + MONTHNAMES[recurrenceMonthOfYear] + ")";
			}
			desc = "(" + desc + ")";
		}
		return desc;
	}
		
	private String weekDaysToString() {
		String res = "";
		if (recurrenceSunday)
			res += "," + SUNDAY;
		if (recurrenceMonday)
			res += "," + MONDAY;
		if (recurrenceTuesday)
			res += "," + TUESDAY;
		if (recurrenceWednesday)
			res += "," + WEDNESDAY;
		if (recurrenceThursday)
			res += "," + THURSDAY;
		if (recurrenceFriday)
			res += "," + FRIDAY;
		if (recurrenceSaturday)
			res += "," + SATURDAY;
		if (res.length() > 0) {
			res = res.substring(1);
		}
		return res;
	}

//	private void stringToWeekDays(String str) {
//		if (str == null)
//			str = "";
//		recurrenceSunday = str.contains(SUNDAY);
//		recurrenceMonday = str.contains(MONDAY);
//		recurrenceTuesday = str.contains(TUESDAY);
//		recurrenceWednesday = str.contains(WEDNESDAY);
//		recurrenceThursday = str.contains(THURSDAY);
//		recurrenceFriday = str.contains(FRIDAY);
//		recurrenceSaturday = str.contains(SATURDAY);
//	}
	
	public boolean canPlay() {
		
		return canPlay(new Date());
	}
	
	@SuppressWarnings("deprecation")
	public boolean canPlay(Date d) {
		if (useDisplayTime)
			;//cal.setTimeZone(displayTimeZone);
		else {
			;//cal.setTimeZone(minutesToTimeZone(Integer.parseInt(timeZone)));
		}
		
		if (!useSchedule || (startDate == null))
			return true;

		Date t = toTime(d);
		d = toDate(d);
		
		startDate = toDate(startDate);
		endDate = toDate(endDate);
		startTime = toTime(startTime);
		endTime = toTime(endTime);
		
		if (d.before(startDate))
			return false;
		if (!noEndDate && (endDate != null) && d.after(endDate))
			return false;
		if (!allDay && (startTime != null) && (endTime != null)) {
			if (startTime.before(endTime)) {
				if (t.before(startTime) || t.after(endTime))
					return false;
			} else {
				if (t.before(startTime) && t.after(endTime))
					return false;
			}
		}
		//-------------------------
		int weekday = d.getDay();
		int dayOfMonth = d.getDate();
		if (recurrenceFrequency < 1)
			recurrenceFrequency = 1;
		if (recurrenceType == RecurrenceType.Daily) {
			int days = RiseUtils.diffDays(startDate, d);
			if ((days % recurrenceFrequency) != 0)
				return false;
		} else if (recurrenceType == RecurrenceType.Weekly) {
			int weeks = diffWeeks(startDate, d);
			//unit test the diffWeeks function
//			String utres ="";
//			utres += diffWeeksUnitTest(new Date(110,3,11), new Date(110,3,11)); //year 2010 in Java = 2010
//			utres += diffWeeksUnitTest(new Date(110,3,17), new Date(110,3,17));
//			utres += diffWeeksUnitTest(new Date(110,3,11), new Date(110,3,17));
//			utres += diffWeeksUnitTest(new Date(110,3,10), new Date(110,3,11));
//			utres += diffWeeksUnitTest(new Date(110,3,10), new Date(110,3,17));
//			utres += diffWeeksUnitTest(new Date(110,3,11), new Date(110,3,18));
//			Window.alert(utres);
			if ((weeks % recurrenceFrequency) != 0)
				return false;
						
			if (!(((weekday == 0) && recurrenceSunday)
					|| ((weekday == 1) && recurrenceMonday)
					|| ((weekday == 2) && recurrenceTuesday)
					|| ((weekday == 3) && recurrenceWednesday)
					|| ((weekday == 4) && recurrenceThursday)
					|| ((weekday == 5) && recurrenceFriday)
					|| ((weekday == 6) && recurrenceSaturday)
					)) 
				return false;
		} else if (recurrenceType == RecurrenceType.Monthly) {
			int months = diffMonths(startDate, d);
			if (recurrenceIsAbsolute) {
				if ((recurrenceDayOfMonth != dayOfMonth) || ((months % recurrenceFrequency) != 0))
					return false;
			} else {
				if ((weekday != recurrenceDayOfWeek) || ((months % recurrenceFrequency) != 0)) 
					return false;
				// check if the last week of the month is selected recurrenceWeekOfMonth = 4
				if (recurrenceWeekOfMonth == 4) {
					if (dayOfMonth <= (daysInMonth(d) - 7))
						return false;
				} else if ((recurrenceWeekOfMonth != (dayOfMonth - 1) / 7))
						return false;
			}
		} else if (recurrenceType == RecurrenceType.Yearly) {
			if (recurrenceIsAbsolute) {
				if (!(((d.getMonth() == recurrenceMonthOfYear) && (dayOfMonth == recurrenceDayOfMonth))))
					return false;
			} else {
		          if ((weekday != recurrenceDayOfWeek) || (d.getMonth() != recurrenceMonthOfYear))
		        	  return false;
		          //check if the last week of the month is selected @RecurrenceWeekOfMonth=4
		          if (recurrenceWeekOfMonth == 4) {
		        	  if ((dayOfMonth <=(daysInMonth(d)-7)))
		        			  return false;
		          } else if (recurrenceWeekOfMonth != ((dayOfMonth - 1) / 7))
		            	  return false;
			}
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	public static int daysInMonth(Date d) {
		int[] daysInMonths = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// check is leap year
		daysInMonths[1] = (d.getYear() % 4 == 0) ? 29 : 28;
		return daysInMonths[d.getMonth()];
	}

	// Moved to RiseUtils
//	private static int diffDays(Date fromDate, Date toDate) {
//		return (int) ((toDate.getTime() - fromDate.getTime()) / msecInOneDay);
//	}

	@SuppressWarnings("deprecation")
	private static int diffMonths(Date fromDate, Date toDate) {
		return ((toDate.getYear() - fromDate.getYear()) * 12) + toDate.getMonth() - fromDate.getMonth();
	}
	
	@SuppressWarnings("deprecation")
	private static int diffWeeks(Date fromDate, Date toDate) {
		int days = RiseUtils.diffDays(fromDate, toDate);
		int weeks = (days + fromDate.getDay()) / 7;
		return weeks;
	}

	@SuppressWarnings("unused")
	private static String diffWeeksUnitTest(Date fromDate, Date toDate) {
		int weeks = diffWeeks(fromDate, toDate);
		return Integer.toString(weeks) +" weeks between " + fromDate. toString() +" and "+ toDate.toString() + "\r\n";
	}

	@SuppressWarnings("deprecation")
	private static Date toDate(Date d) {
		return (d == null) ? null : new Date(d.getYear(), d.getMonth(), d.getDate());
	}

	@SuppressWarnings("deprecation")
	private Date toTime(Date d) {
		return (d == null) ? null : new Date(0,0,0,d.getHours(),d.getMinutes(), d.getSeconds());
	}
	
	public boolean getUseSchedule() {
		return useSchedule;
	}

	public void setUseSchedule(boolean useSchedule) {
		this.useSchedule = useSchedule;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean getNoEndDate() {
		return noEndDate;
	}

	public void setNoEndDate(boolean noEndDate) {
		this.noEndDate = noEndDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean getAllDay() {
		return allDay;
	}

	public void setAllDay(boolean noEndTime) {
		this.allDay = noEndTime;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getStartTimeString() {
		return startTimeString;
	}

	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public String getEndTimeString() {
		return endTimeString;
	}

	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public boolean getUseDisplayTime() {
		return useDisplayTime;
	}

	public void setUseDisplayTime(boolean useDisplayTime) {
		this.useDisplayTime = useDisplayTime;
	}

	public RecurrenceType getRecurrenceType() {
		return recurrenceType;
	}

	public void setRecurrenceType(RecurrenceType recurrenceType) {
		this.recurrenceType = recurrenceType;
	}

	public int getRecurrenceFrequency() {
		return recurrenceFrequency;
	}

	public void setRecurrenceFrequency(int recurrenceFrequency) {
		this.recurrenceFrequency = recurrenceFrequency;
	}

	public boolean getRecurrenceSunday() {
		return recurrenceSunday;
	}

	public void setRecurrenceSunday(boolean recurrenceSunday) {
		this.recurrenceSunday = recurrenceSunday;
	}

	public boolean getRecurrenceMonday() {
		return recurrenceMonday;
	}

	public void setRecurrenceMonday(boolean recurrenceMonday) {
		this.recurrenceMonday = recurrenceMonday;
	}

	public boolean getRecurrenceTuesday() {
		return recurrenceTuesday;
	}

	public void setRecurrenceTuesday(boolean recurrenceTuesday) {
		this.recurrenceTuesday = recurrenceTuesday;
	}

	public boolean getRecurrenceWednesday() {
		return recurrenceWednesday;
	}

	public void setRecurrenceWednesday(boolean recurrenceWednesday) {
		this.recurrenceWednesday = recurrenceWednesday;
	}

	public boolean getRecurrenceThursday() {
		return recurrenceThursday;
	}

	public void setRecurrenceThursday(boolean recurrenceThursday) {
		this.recurrenceThursday = recurrenceThursday;
	}

	public boolean getRecurrenceFriday() {
		return recurrenceFriday;
	}

	public void setRecurrenceFriday(boolean recurrenceFriday) {
		this.recurrenceFriday = recurrenceFriday;
	}

	public boolean getRecurrenceSaturday() {
		return recurrenceSaturday;
	}

	public void setRecurrenceSaturday(boolean recurrenceSaturday) {
		this.recurrenceSaturday = recurrenceSaturday;
	}

	public int getRecurrenceDayOfWeek() {
		return recurrenceDayOfWeek;
	}

	public void setRecurrenceDayOfWeek(int recurrenceDayOfWeek) {
		this.recurrenceDayOfWeek = recurrenceDayOfWeek;
	}

	public int getRecurrenceWeekOfMonth() {
		return recurrenceWeekOfMonth;
	}

	public void setRecurrenceWeekOfMonth(int recurrenceWeekOfMonth) {
		this.recurrenceWeekOfMonth = recurrenceWeekOfMonth;
	}

	public int getRecurrenceMonthOfYear() {
		return recurrenceMonthOfYear;
	}

	public void setRecurrenceMonthOfYear(int recurrenceMonthOfYear) {
		this.recurrenceMonthOfYear = recurrenceMonthOfYear;
	}

	public boolean getRecurrenceIsAbsolute() {
		return recurrenceIsAbsolute;
	}

	public void setRecurrenceIsAbsolute(boolean recurrenceIsAbsolute) {
		this.recurrenceIsAbsolute = recurrenceIsAbsolute;
	}

	public void setRecurrenceDayOfMonth(int recurrenceDayOfMonth) {
		this.recurrenceDayOfMonth = recurrenceDayOfMonth;
	}

	public int getRecurrenceDayOfMonth() {
		return recurrenceDayOfMonth;
	}
	
	public TimeLineInfo copy() {
		TimeLineInfo copy = new TimeLineInfo();
		
		copy.setUseSchedule(useSchedule);
		if (startDate != null)
			copy.setStartDate(new Date(startDate.getTime()));
		if (endDate != null)
			copy.setEndDate(new Date(endDate.getTime()));
		copy.setNoEndDate(noEndDate);

		if (startTime != null)
			copy.setStartTime(new Date(startTime.getTime()));
		if (endTime != null)
			copy.setEndTime(new Date(endTime.getTime()));
		copy.setAllDay(allDay);
		
		copy.setStartDateString(startDateString);
		copy.setStartTimeString(startTimeString);
		copy.setEndDateString(endDateString);
		copy.setEndTimeString(endTimeString);

		copy.setTimeZone(timeZone);
		copy.setUseDisplayTime(useDisplayTime);

		copy.setRecurrenceType(recurrenceType);
		copy.setRecurrenceFrequency(recurrenceFrequency);
		
		copy.setRecurrenceSunday(recurrenceSunday);
		copy.setRecurrenceMonday(recurrenceMonday);
		copy.setRecurrenceTuesday(recurrenceTuesday);
		copy.setRecurrenceWednesday(recurrenceWednesday);
		copy.setRecurrenceThursday(recurrenceThursday);
		copy.setRecurrenceFriday(recurrenceFriday);
		copy.setRecurrenceSaturday(recurrenceSaturday);

		copy.setRecurrenceIsAbsolute(recurrenceIsAbsolute);

		copy.setRecurrenceDayOfWeek(recurrenceDayOfWeek);
		copy.setRecurrenceDayOfMonth(recurrenceDayOfMonth);
		copy.setRecurrenceWeekOfMonth(recurrenceWeekOfMonth);
		copy.setRecurrenceMonthOfYear(recurrenceMonthOfYear);
		
		return copy;
	}

}