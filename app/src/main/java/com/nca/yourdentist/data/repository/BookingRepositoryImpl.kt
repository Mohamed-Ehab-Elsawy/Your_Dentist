package com.nca.yourdentist.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nca.yourdentist.data.model.Dentist
import com.nca.yourdentist.domain.repository.BookingRepository
import com.nca.yourdentist.utils.Constant
import kotlinx.coroutines.tasks.await

class BookingRepositoryImpl(
    firestore: FirebaseFirestore
) : BookingRepository {
    private val dentistCollection = firestore.collection(Constant.DENTIST_COLLECTIONS)

    override suspend fun fetchDentists(
        selectedCity: Int,
        selectedArea: Int
    ): Result<List<Dentist>> {
        return try {
            Log.e(
                "FirestoreQuery",
                "Fetching dentists for city: $selectedCity, area: $selectedArea"
            )

            val snapshot = dentistCollection
                .whereEqualTo("city", selectedCity)
                .whereEqualTo("area", selectedArea)
                .get()
                .await()

            Log.e("FirestoreQuery", "Documents found: ${snapshot.documents.size}")

            Result.success(snapshot.documents.mapNotNull { it.toObject(Dentist::class.java) })
        } catch (t: Throwable) {
            Log.e("FirestoreQuery", "Error fetching dentists: ${t.localizedMessage}")
            Result.failure(t)
        }
    }
}