package br.lumago.solix.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.lumago.solix.data.dao.CustomersDao
import br.lumago.solix.data.dao.PaymentsDao
import br.lumago.solix.data.database.schemas.MockSchema
import br.lumago.solix.data.entities.Addresses
import br.lumago.solix.data.entities.Counts
import br.lumago.solix.data.entities.Customers
import br.lumago.solix.data.entities.Enterprises
import br.lumago.solix.data.entities.Groups
import br.lumago.solix.data.entities.Items
import br.lumago.solix.data.entities.Payments
import br.lumago.solix.data.entities.Products

@Database(
    version = 1,
    entities = [
        Groups::class, Enterprises::class, Customers::class, Addresses::class, Payments::class,
        Products::class, Counts::class, Items::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paymentsDao(): PaymentsDao
    abstract fun customersDao() : CustomersDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL(MockSchema.INSERT_GROUP.trimIndent())
                        db.execSQL(MockSchema.INSERT_ENTERPRISE.trimIndent())
                        db.execSQL(MockSchema.INSERT_CUSTOMER.trimIndent())
                        db.execSQL(MockSchema.INSERT_CUSTOMER_2.trimIndent())
                    }
                }
                ).build()
                Instance = instance
                instance
            }
        }
    }
}