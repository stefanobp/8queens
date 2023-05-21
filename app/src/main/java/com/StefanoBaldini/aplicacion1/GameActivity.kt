package com.StefanoBaldini.aplicacion1
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.StefanoBaldini.myapplication.R

class GameActivity : AppCompatActivity() {
    private lateinit var board: Array<Array<Boolean>>
    private val boardSize = 8
    private val cellSize = 100 // tamaño de cada celda en píxeles
    private var moveCount = 0
    private var isGameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        board = Array(boardSize) { Array(boardSize) { false } }
        setupBoardView()
        updateBoardView()
    }

    private fun setupBoardView() {
        val boardLayout = findViewById<LinearLayout>(R.id.boardLayout)
        boardLayout.orientation = LinearLayout.VERTICAL

        for (row in 0 until boardSize) {
            val rowLayout = LinearLayout(this)
            rowLayout.orientation = LinearLayout.HORIZONTAL
            rowLayout.gravity = Gravity.CENTER

            for (col in 0 until boardSize) {
                val imageView = ImageView(this)
                val layoutParams = LinearLayout.LayoutParams(cellSize, cellSize)
                imageView.layoutParams = layoutParams
                imageView.setImageResource(R.drawable.empty_cell)
                imageView.setOnClickListener { handleCellClick(row, col) }
                rowLayout.addView(imageView)
            }

            boardLayout.addView(rowLayout)
        }
    }

    private fun handleCellClick(row: Int, col: Int) {
        if (board[row][col]) {
            removeQueen(row, col)
        } else {
            placeQueen(row, col)
        }
    }

    private fun areAllQueensPlaced(): Boolean {
        var count = 0
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (board[row][col]) {
                    count++
                }
            }
        }
        return count == 8
    }

    private fun placeQueen(row: Int, col: Int) {
        if (!isCellCovered(row, col)) {
            board[row][col] = true
            moveCount++
            updateBoardView()
            checkNoPossibleMoves()
            if (areAllQueensPlaced()) {
                isGameOver = true
                Toast.makeText(
                    this,
                    "¡Has ganado! Has colocado las 8 reinas.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun removeQueen(row: Int, col: Int) {
        board[row][col] = false
        updateBoardView()
        checkNoPossibleMoves()
    }

    private fun isCellCovered(row: Int, col: Int): Boolean {
        // verifica filas y columnas
        for (i in 0 until boardSize) {
            if (board[row][i] || board[i][col]) {
                return true
            }
        }

        // verifica diagonales
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if ((i + j == row + col || i - j == row - col) && board[i][j]) {
                    return true
                }
            }
        }

        return false
    }

    private fun checkNoPossibleMoves() {
        if (moveCount == boardSize && !areAllQueensPlaced()) {
            var noPossibleMoves = true

            for (row in 0 until boardSize) {
                for (col in 0 until boardSize) {
                    if (!board[row][col] && !isCellCovered(row, col)) {
                        noPossibleMoves = false
                        break
                    }
                }
                if (!noPossibleMoves) {
                    break
                }
            }

            if (noPossibleMoves) {
                // alerta de que no hay movimientos posibles
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("¡Sin movimientos posibles!")
                    .setMessage("Ya no quedan movimientos disponibles. Puedes eliminar una reina para continuar.")
                    .setPositiveButton("Aceptar") { _, _ ->
                        // Acción al aceptar la alerta (opcional)
                    }
                    .setCancelable(false)
                    .create()

                alertDialog.show()
            }
        }
    }




    private fun hasPossibleMoves(): Boolean {
        for (row in 0 until boardSize) {
            for (col in 0 until boardSize) {
                if (!board[row][col] && !isCellCovered(row, col)) {
                    return true
                }
            }
        }
        return false
    }

    private fun updateBoardView() {
        val boardLayout = findViewById<LinearLayout>(R.id.boardLayout)
        val moveCountTextView = findViewById<TextView>(R.id.moveCountTextView)

        for (row in 0 until boardSize) {
            val rowLayout = boardLayout.getChildAt(row) as LinearLayout

            for (col in 0 until boardSize) {
                val imageView = rowLayout.getChildAt(col) as ImageView
                val imageResource = if (board[row][col]) R.drawable.queen else R.drawable.empty_cell
                imageView.setImageResource(imageResource)
            }
        }

        moveCountTextView.text = "Movimientos: $moveCount"
    }
}
