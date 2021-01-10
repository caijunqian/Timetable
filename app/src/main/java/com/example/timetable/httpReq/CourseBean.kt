package com.example.timetable.httpReq
import com.example.timetable.Subject
import com.google.gson.annotations.SerializedName


data class CourseBean(
    @SerializedName("code")
    var code: Int?,
    @SerializedName("data")
    var `data`: List<Subject>?,
    @SerializedName("msg")
    var msg: Int?
)
//{
//    data class Data(
//        @SerializedName("classroom")
//        var classroom: String?,
//        @SerializedName("courseId")
//        var courseId: Int?,
//        @SerializedName("courseName")
//        var courseName: String?,
//        @SerializedName("courseTimeId")
//        var courseTimeId: Int?,
//        @SerializedName("endLesson")
//        var endLesson: String?,
//        @SerializedName("endWeek")
//        var endWeek: Int?,
//        @SerializedName("startLesson")
//        var startLesson: String?,
//        @SerializedName("startWeek")
//        var startWeek: Int?,
//        @SerializedName("termId")
//        var termId: Int?,
//        @SerializedName("userId")
//        var userId: Int?,
//        @SerializedName("weekday")
//        var weekday: Int?
//    )
//}

