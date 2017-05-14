// This file is part of Mocha Doom.
// Copyright (C) 1993-1996  id Software, Inc.
// Copyright (C) 2010-2013  Victor Epitropou
// Copyright (C) 2016-2017  Alexandre-Xavier Labonté-Lamoureux
//
// Mocha Doom is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Mocha Doom is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along withMocha Doom.  If not, see <http://www.gnu.org/licenses/>.

package p;

public class slidename_t {

	public slidename_t() {

	}

	public slidename_t(String frontFrame1, String frontFrame2,
			String frontFrame3, String frontFrame4, String backFrame1,
			String backFrame2, String backFrame3, String backFrame4) {
		this.frontFrame1 = frontFrame1;
		this.frontFrame2 = frontFrame2;
		this.frontFrame3 = frontFrame3;
		this.frontFrame4 = frontFrame4;
		this.backFrame1 = backFrame1;
		this.backFrame2 = backFrame2;
		this.backFrame3 = backFrame3;
		this.backFrame4 = backFrame4;
	}

	String frontFrame1;
	String frontFrame2;
	String frontFrame3;
	String frontFrame4;
	String backFrame1;
	String backFrame2;
	String backFrame3;
	String backFrame4;

}