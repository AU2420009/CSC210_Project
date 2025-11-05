"use client";

import { QuizQuestion } from "@/types";
import { useState } from "react";
import { saveQuizScore } from "@/lib/progress";

interface QuizProps {
  questions: QuizQuestion[];
  lessonId: string;
  onComplete: () => void;
}

export default function Quiz({ questions, lessonId, onComplete }: QuizProps) {
  const [currentQuestion, setCurrentQuestion] = useState(0);
  const [selectedAnswer, setSelectedAnswer] = useState<number | null>(null);
  const [showExplanation, setShowExplanation] = useState(false);
  const [score, setScore] = useState(0);
  const [isComplete, setIsComplete] = useState(false);

  const handleAnswerSelect = (index: number) => {
    if (showExplanation) return;
    setSelectedAnswer(index);
  };

  const handleSubmit = () => {
    if (selectedAnswer === null) return;
    
    setShowExplanation(true);
    
    if (selectedAnswer === questions[currentQuestion].correctAnswer) {
      setScore(score + 1);
    }
  };

  const handleNext = () => {
    if (currentQuestion < questions.length - 1) {
      setCurrentQuestion(currentQuestion + 1);
      setSelectedAnswer(null);
      setShowExplanation(false);
    } else {
      const finalScore = Math.round(((score + (selectedAnswer === questions[currentQuestion].correctAnswer ? 1 : 0)) / questions.length) * 100);
      saveQuizScore(lessonId, finalScore);
      setIsComplete(true);
    }
  };

  if (isComplete) {
    const finalScore = Math.round((score / questions.length) * 100);
    return (
      <div className="bg-gradient-to-br from-blue-50 to-indigo-50 rounded-xl p-8 text-center border border-blue-200">
        <div className="text-6xl mb-4">
          {finalScore >= 80 ? "🎉" : finalScore >= 60 ? "👍" : "📚"}
        </div>
        <h3 className="text-2xl font-bold text-zinc-900 mb-2">Quiz Complete!</h3>
        <p className="text-4xl font-bold text-blue-600 mb-4">{finalScore}%</p>
        <p className="text-zinc-600 mb-6">
          You got {score} out of {questions.length} questions correct.
        </p>
        <button
          onClick={onComplete}
          className="bg-gradient-to-r from-blue-500 to-blue-600 text-white font-semibold py-3 px-8 rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all duration-200 shadow-lg hover:shadow-xl"
        >
          Continue Learning
        </button>
      </div>
    );
  }

  const question = questions[currentQuestion];
  const isCorrect = selectedAnswer === question.correctAnswer;

  return (
    <div className="bg-white rounded-xl border border-zinc-200 overflow-hidden">
      <div className="bg-gradient-to-r from-indigo-500 to-purple-600 p-6 text-white">
        <div className="flex items-center justify-between mb-2">
          <span className="text-sm font-medium opacity-90">
            Question {currentQuestion + 1} of {questions.length}
          </span>
          <span className="text-sm font-medium opacity-90">
            Score: {score}/{currentQuestion + (showExplanation ? 1 : 0)}
          </span>
        </div>
        <div className="w-full bg-white/20 rounded-full h-2">
          <div 
            className="bg-white h-full rounded-full transition-all duration-300"
            style={{ width: `${((currentQuestion + 1) / questions.length) * 100}%` }}
          />
        </div>
      </div>

      <div className="p-6">
        <h3 className="text-xl font-semibold text-zinc-900 mb-6">
          {question.question}
        </h3>

        <div className="space-y-3 mb-6">
          {question.options.map((option, index) => {
            let buttonClass = "w-full text-left p-4 rounded-xl border-2 transition-all duration-200 ";
            
            if (!showExplanation) {
              buttonClass += selectedAnswer === index
                ? "border-blue-500 bg-blue-50 text-blue-900"
                : "border-zinc-200 bg-white hover:border-blue-300 hover:bg-blue-50 text-zinc-700";
            } else {
              if (index === question.correctAnswer) {
                buttonClass += "border-green-500 bg-green-50 text-green-900";
              } else if (index === selectedAnswer) {
                buttonClass += "border-red-500 bg-red-50 text-red-900";
              } else {
                buttonClass += "border-zinc-200 bg-zinc-50 text-zinc-500";
              }
            }

            return (
              <button
                key={index}
                onClick={() => handleAnswerSelect(index)}
                disabled={showExplanation}
                className={buttonClass}
              >
                <div className="flex items-center gap-3">
                  <div className={`w-6 h-6 rounded-full border-2 flex items-center justify-center flex-shrink-0 ${
                    showExplanation && index === question.correctAnswer
                      ? "border-green-500 bg-green-500 text-white"
                      : showExplanation && index === selectedAnswer
                      ? "border-red-500 bg-red-500 text-white"
                      : selectedAnswer === index
                      ? "border-blue-500 bg-blue-500 text-white"
                      : "border-zinc-300"
                  }`}>
                    {showExplanation && index === question.correctAnswer && "✓"}
                    {showExplanation && index === selectedAnswer && index !== question.correctAnswer && "✗"}
                  </div>
                  <span className="font-medium">{option}</span>
                </div>
              </button>
            );
          })}
        </div>

        {showExplanation && (
          <div className={`p-4 rounded-xl mb-6 ${
            isCorrect ? "bg-green-50 border border-green-200" : "bg-blue-50 border border-blue-200"
          }`}>
            <p className={`font-semibold mb-2 ${isCorrect ? "text-green-900" : "text-blue-900"}`}>
              {isCorrect ? "✓ Correct!" : "ℹ️ Explanation"}
            </p>
            <p className={isCorrect ? "text-green-800" : "text-blue-800"}>
              {question.explanation}
            </p>
          </div>
        )}

        <div className="flex gap-3">
          {!showExplanation ? (
            <button
              onClick={handleSubmit}
              disabled={selectedAnswer === null}
              className="flex-1 bg-gradient-to-r from-blue-500 to-blue-600 text-white font-semibold py-3 px-6 rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed shadow-lg hover:shadow-xl"
            >
              Submit Answer
            </button>
          ) : (
            <button
              onClick={handleNext}
              className="flex-1 bg-gradient-to-r from-green-500 to-green-600 text-white font-semibold py-3 px-6 rounded-xl hover:from-green-600 hover:to-green-700 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              {currentQuestion < questions.length - 1 ? "Next Question" : "Finish Quiz"}
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
