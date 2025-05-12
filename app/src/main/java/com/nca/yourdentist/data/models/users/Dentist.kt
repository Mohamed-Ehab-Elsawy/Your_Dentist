package com.nca.yourdentist.data.models.users

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.nca.yourdentist.utils.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dentist(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val profileImage: String? = null,
    val clinic: Clinic? = null,
    val sessionPrice: String? = "300",
    val rate: Float = 0.0f,
    val patientsNumber: String = "0",
    val workingSchedule: List<WorkingDay> = emptyList(),
    val createdAt: Timestamp = Timestamp.now(),
    val type: String = Constant.DENTIST
) : Parcelable

@Parcelize
data class Clinic(
    val city: Int? = null,
    val area: Int? = null,
    val clinicLocation: String? = null,
    var clinicPhoneNumber: String? = null
) : Parcelable

@Parcelize
data class WorkingDay(
    val day: String = "",
    val hours: List<String> = emptyList<String>()
) : Parcelable

data class CityArea(val nameEn: String, val nameAr: String) {
    companion object {
        val citiesMap = hashMapOf(
            1 to CityArea("Cairo", "القاهرة"),
            2 to CityArea("Giza", "الجيزة")
        )
        val cairoAreasMap = hashMapOf(
            1 to CityArea("El Marg", "المرج"),
            2 to CityArea("Matareya", "المطرية"),
            3 to CityArea("Ain Shams", "عين شمس"),
            4 to CityArea("First El Salam", "أول السلام"),
            5 to CityArea("Second El Salam", "ثان السلام"),
            6 to CityArea("El Nozha", "حي النزهة"),
            7 to CityArea("Heliopolis", "مصر الجديدة"),
            8 to CityArea("East Nasr City", "شرق مدينة نصر"),
            9 to CityArea("West Nasr City", "غرب مدينة نصر"),
            10 to CityArea("El Waily", "الوايلي"),
            11 to CityArea("Bab El Shaaria", "باب الشعرية"),
            12 to CityArea("Downtown", "وسط القاهرة"),
            13 to CityArea("El Mosky", "الموسكى"),
            14 to CityArea("El Azbakeya", "الأزبكية"),
            15 to CityArea("Abdeen", "عابدين"),
            16 to CityArea("Boulak", "بولاق"),
            17 to CityArea("West District", "حي غرب"),
            18 to CityArea("Manshiyat Naser", "منشأة ناصر"),
            19 to CityArea("El Zeitoun", "الزيتون"),
            20 to CityArea("Hadayek El Kobba", "حدائق القبة"),
            21 to CityArea("El Zawya El Hamra", "الزاوية الحمراء"),
            22 to CityArea("El Sharabiya", "الشرابية"),
            23 to CityArea("El Sahel", "الساحل"),
            24 to CityArea("Shubra", "شبرا"),
            25 to CityArea("Rod El Farag", "روض الفرج"),
            26 to CityArea("El Amiriya", "الأميرية"),
            27 to CityArea("El Sayeda Zeinab", "السيدة زينب"),
            28 to CityArea("Old Cairo", "مصر القديمة"),
            29 to CityArea("El Khalifa", "الخليفة"),
            30 to CityArea("El Mokattam", "المقطم"),
            31 to CityArea("El Basatin", "البساتين"),
            32 to CityArea("Dar El Salam", "دار السلام"),
            33 to CityArea("Maadi", "المعادي"),
            34 to CityArea("Tora", "طرة"),
            35 to CityArea("Helwan", "حلوان"),
            36 to CityArea("El Tebbin", "التبين"),
            37 to CityArea("15th of May City", "مدينة 15 مايو"),
            38 to CityArea("El Maasara", "المعصرة")
        )
        val gizaAreasMap = hashMapOf(
            1 to CityArea("Ard El Lewa", "أرض اللواء"),
            2 to CityArea("Imbaba", "إمبابة"),
            3 to CityArea("Giza City", "مدينة الجيزة"),
            4 to CityArea("Talbia", "الطالبية"),
            5 to CityArea("Agouza", "العجوزة"),
            6 to CityArea("Omraniya", "العمرانية"),
            7 to CityArea("Kit Kat", "الكيت كات"),
            8 to CityArea("Moneeb", "المنيب"),
            9 to CityArea("El Monira", "المنيرة"),
            10 to CityArea("Haram", "الهرم"),
            11 to CityArea("Warraq", "الوراق"),
            12 to CityArea("Bulaq El Dakrour", "بولاق الدكرور"),
            13 to CityArea("Giza South", "جنوب الجيزة"),
            14 to CityArea("Dokki", "الدقي"),
            15 to CityArea("Mohandessin", "المهندسين")
        )
    }
}