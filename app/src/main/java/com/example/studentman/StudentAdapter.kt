package com.example.studentman

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(private val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        holder.imageEdit.setOnClickListener {
            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.dialog_add_student, null)
            val builder = AlertDialog.Builder(holder.itemView.context)
                .setTitle("Chỉnh sửa sinh viên")
                .setView(dialogView)
                .setPositiveButton("Cập nhật") { _, _ ->
                    val name = dialogView.findViewById<EditText>(R.id.edit_student_name).text.toString()
                    val id = dialogView.findViewById<EditText>(R.id.edit_student_id).text.toString()
                    students[position].studentName = name
                    students[position].studentId = id
                    notifyItemChanged(position)
                }
                .setNegativeButton("Huỷ", null)

            // Gán thông tin hiện tại vào dialog
            dialogView.findViewById<EditText>(R.id.edit_student_name).setText(student.studentName)
            dialogView.findViewById<EditText>(R.id.edit_student_id).setText(student.studentId)

            builder.show()
        }

        holder.imageRemove.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Xoá sinh viên")
                .setMessage("Bạn có chắc chắn muốn xoá sinh viên này?")
                .setPositiveButton("Xoá") { _, _ ->
                    val removedStudent = students[position]
                    students.removeAt(position)
                    notifyItemRemoved(position)
                    (holder.itemView.context as MainActivity).showUndoSnackbar(removedStudent, position, this, students)
                }
                .setNegativeButton("Huỷ", null)
                .show()
        }

    }
}