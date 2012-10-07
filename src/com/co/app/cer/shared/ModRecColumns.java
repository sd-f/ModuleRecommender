/**
 * 
 */
package com.co.app.cer.shared;

/**
 * enum class for active columns and "with"-Attributes for
 * curriculum node grid
 * 
 * @author Lucas Reeh
 *
 */
public enum ModRecColumns {
	ATTENDANCE_ACTIVE {
		public String toString() {
			return "attendance";
		}
	},
	ATTENDANCE_INACTIVE {
		public String toString() {
			return "attendanceInactive";
		}
	},	
	SEMESTER_TYPE {
		public String toString() {
			return "semesterType.refId";
		}
	},
	RECOMMENDED {
		public String toString() {
			return "recommended";
		}
	},	
	RECOMMENDED_BY_LECTURER {
		public String toString() {
			return "recommendedByLecturer";
		}
	},
	MEDIAN_SEMESTER {
		public String toString() {
			return "semester";
		}
	};
}
