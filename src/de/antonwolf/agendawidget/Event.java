/*
 * Copyright (C) 2011 by Anton Wolf
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.antonwolf.agendawidget;

import android.text.format.Time;

public final class Event {
	public boolean allDay = false;
	public int color;
	public int endDay;
	public long endMillis;
	public Time endTime;
	public boolean hasAlarm;
	public boolean isBirthday = false;
	public String location;
	public long startMillis;
	public Time startTime;
	public int startDay;
	public String title;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Event))
			return false;

		final Event other = (Event) o;

		return isBirthday && other.isBirthday
				&& other.startDay == this.startDay
				&& other.title.equals(this.title);
	}
}