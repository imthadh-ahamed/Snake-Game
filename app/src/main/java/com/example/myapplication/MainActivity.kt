package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.Activity
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast

import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val board = findViewById<RelativeLayout>(R.id.board)
        val border = findViewById<RelativeLayout>(R.id.relativeLayout)
        val lilu = findViewById<LinearLayout>(R.id.lilu)
        val upButton = findViewById<Button>(R.id.up)
        val downButton = findViewById<Button>(R.id.down)
        val leftButton = findViewById<Button>(R.id.left)
        val rightButton = findViewById<Button>(R.id.right)
        val pauseButton = findViewById<Button>(R.id.pause)
        val newgame = findViewById<Button>(R.id.new_game)
        val resume = findViewById<Button>(R.id.resume)
        val playagain = findViewById<Button>(R.id.playagain)
        val score = findViewById<Button>(R.id.score)
        val score2 = findViewById<Button>(R.id.score2)
        val meat = ImageView(this)
        val snake = ImageView(this)
        val snakeSegments =
            mutableListOf(snake) // Keep track of the position of each snake segment
        val handler = Handler()
        var delayMillis = 30L // Update snake position every 100 milliseconds
        var currentDirection = "right" // Start moving right by default
        var scorex = 0


        board.visibility = View.INVISIBLE
        newgame.visibility = View.VISIBLE
        score.visibility = View.INVISIBLE
        score2.visibility = View.INVISIBLE
        playagain.visibility = View.INVISIBLE
        resume.visibility = View.INVISIBLE


        newgame.setOnClickListener {
            board.visibility = View.VISIBLE
            newgame.visibility = View.INVISIBLE
            resume.visibility = View.INVISIBLE
            score2.visibility = View.VISIBLE
            

            snake.setImageResource(R.drawable.snake)
            snake.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(snake)
            snakeSegments.add(snake) // Add the new snake segment to the list


            var snakeX = snake.x
            var snakeY = snake.y

            meat.setImageResource(R.drawable.meat)
            meat.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(meat)

            val random = Random() // create a Random object
            val randomX = random.nextInt(801) - 400 // generate a random x-coordinate between -400 and 400
            val randomY = random.nextInt(801) - 400 // generate a random y-coordinate between -400 and 400

            meat.x = randomX.toFloat()
            meat.y = randomY.toFloat()

            // Function to check collision between snake and food
            fun checkFoodCollision() {
                val distanceThreshold = 50
                val distance = sqrt((snake.x - meat.x).pow(2) + (snake.y - meat.y).pow(2))

                // Check if the distance between the snake head and the meat is less than the threshold
                if (distance < distanceThreshold) {

                    // Increase score and spawn new food

                    // Create a new ImageView for the additional snake segment
                    val newSnake = ImageView(this)
                    newSnake.setImageResource(R.drawable.snake)
                    newSnake.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    board.addView(newSnake)
                    snakeSegments.add(newSnake) // Add the new snake segment to the list

                    val randomX = random.nextInt(801) - -100
                    val randomY = random.nextInt(801) - -100
                    meat.x = randomX.toFloat()
                    meat.y = randomY.toFloat()
                    delayMillis-- // Reduce delay value by 1
                    scorex++
                    score2.text =   "Score : " + scorex.toString() // Update delay text view
                }
            }

            // Snake movement logic
            val runnable = object : Runnable {
                override fun run() {

                    // Update snake segments positions
                    for (i in snakeSegments.size - 1 downTo 1) { // Update the position of each snake segment except for the head
                        snakeSegments[i].x = snakeSegments[i - 1].x
                        snakeSegments[i].y = snakeSegments[i - 1].y
                    }

                    // Move snake according to current direction
                    when (currentDirection) {
                        "up" -> {
                            // Handle boundary and collision conditions
                            snakeY -= 10
                            if (snakeY < -490) { // Check if the ImageView goes off the top of the board
                                // Handle game over condition
                                snakeY = -490f
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE
                                score.text =   "Your score is  " + scorex.toString() // Update delay text view
                                score.visibility = View.VISIBLE
                                score2.visibility = View.INVISIBLE
                                board.visibility = View.INVISIBLE
                                resume.visibility = View.INVISIBLE
                            }
                            snake.translationY = snakeY
                        }

                        "down" -> {
                            // Handle boundary and collision conditions
                            snakeY += 10
                            val maxY =
                                board.height / 2 - snake.height + 30 // Calculate the maximum y coordinate
                            if (snakeY > maxY) { // Check if the ImageView goes off the bottom of the board
                                // Handle game over condition
                                snakeY = maxY.toFloat()
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE
                                score.text =   "Your score is  " + scorex.toString() // Update delay text view
                                score.visibility = View.VISIBLE
                                score2.visibility = View.INVISIBLE
                                board.visibility = View.INVISIBLE
                                resume.visibility = View.INVISIBLE
                            }
                            snake.translationY = snakeY
                        }

                        "left" -> {
                            // Handle boundary and collision conditions
                            snakeX -= 10
                            if (snakeX < -490) { // Check if the ImageView goes off the top of the board
                                // Handle game over condition
                                snakeX = -490f
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE
                                score.text =   "Your score is  " + scorex.toString() // Update delay text view
                                score.visibility = View.VISIBLE
                                score2.visibility = View.INVISIBLE
                                board.visibility = View.INVISIBLE
                                resume.visibility = View.INVISIBLE
                            }
                            snake.translationX = snakeX
                        }

                        "right" -> {
                            // Handle boundary and collision conditions
                            snakeX += 10
                            val maxX =
                                board.height / 2 - snake.height + 30 // Calculate the maximum y coordinate
                            if (snakeX > maxX) { // Check if the ImageView goes off the bottom of the board
                                // Handle game over condition
                                snakeX = maxX.toFloat()
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE
                                score.text =   "Your score is  " + scorex.toString() // Update delay text view
                                score.visibility = View.VISIBLE
                                score2.visibility = View.INVISIBLE
                                board.visibility = View.INVISIBLE
                                resume.visibility = View.INVISIBLE
                            }
                            snake.translationX = snakeX
                        }

                        "pause" -> {
                            // Pause the game
                            snakeX += 0
                            snake.translationX = snakeX
                            resume.visibility = View.VISIBLE
                            val params = playagain.layoutParams as RelativeLayout.LayoutParams
                            params.addRule(RelativeLayout.CENTER_IN_PARENT)
                            playagain.layoutParams = params
                        }
                    }
                    // Check for food collision and continue game loop
                    checkFoodCollision()
                    handler.postDelayed(this, delayMillis)
                }
            }

            handler.postDelayed(runnable, delayMillis)

            // Set button onClickListeners to update the currentDirection variable when pressed
            upButton.setOnClickListener {
                currentDirection = "up"
            }
            downButton.setOnClickListener {
                currentDirection = "down"
            }
            leftButton.setOnClickListener {
                currentDirection = "left"
            }
            rightButton.setOnClickListener {
                currentDirection = "right"
            }
            pauseButton.setOnClickListener {
                // Pause game and show UI elements
                currentDirection = "pause"
                board.visibility = View.INVISIBLE
                newgame.visibility = View.VISIBLE
                //resume.visibility = View.VISIBLE
            }
            resume.setOnClickListener {
                // Resume game and hide UI elements
                currentDirection = "right"
                board.visibility = View.VISIBLE
                newgame.visibility = View.INVISIBLE
                resume.visibility = View.INVISIBLE

            }
            playagain.setOnClickListener {
                recreate()
            }
        }
    }
}