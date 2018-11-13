//package jp.kentan.cookpad2018.data
//
//import android.arch.lifecycle.LiveData
//import android.arch.lifecycle.MutableLiveData
//import android.util.Log
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import jp.kentan.cookpad2018.data.model.Food
//import org.jetbrains.anko.coroutines.experimental.bg
//
//class FoodRepository {
//
//    private companion object {
//        const val FOODS = "foods"
//    }
//
//    private val sampleFoods: List<Food>
//
//    init {
//        sampleFoods = listOf(
//                Food.EGG,
//                Food("tomato", "トマト"),
//                Food("green_pepper", "ピーマン"),
//                Food("test", "test")
//        ).sortedBy { it.name }
//    }
//
//    fun getFoods() = sampleFoods
//
////    fun getFoods(): LiveData<List<Food>> {
////        val result = MutableLiveData<List<Food>>()
////
////        val query = database.reference.child(FOODS).orderByChild("name")
////
////        query.addListenerForSingleValueEvent(object : ValueEventListener {
////            override fun onDataChange(snapshot: DataSnapshot) {
////                val data = snapshot.children.mapNotNull { it.getValue(Food::class.java) }
////                result.value = data
//////                result.value = snapshot.children.mapNotNull { it.getValue(Food::class.java) }
//////                bg {
//////                    result.postValue(snapshot.children.mapNotNull { it.getValue(Food::class.java) })
//////                }
////            }
////
////            override fun onCancelled(error: DatabaseError) {
////                result.value = emptyList()
////                Log.d(javaClass.simpleName, error.message)
////            }
////        })
////
////        return result
////    }
//}