"use client";

import { Lesson } from "@/types";
import { markLessonComplete, isLessonComplete } from "@/lib/progress";
import { useState, useEffect } from "react";
import Quiz from "./Quiz";

interface LessonViewerProps {
  lesson: Lesson;
  onComplete?: () => void;
}

export default function LessonViewer({ lesson, onComplete }: LessonViewerProps) {
  const [isComplete, setIsComplete] = useState(false);
  const [showQuiz, setShowQuiz] = useState(false);

  useEffect(() => {
    setIsComplete(isLessonComplete(lesson.id));
  }, [lesson.id]);

  const handleComplete = () => {
    markLessonComplete(lesson.id);
    setIsComplete(true);
    if (onComplete) onComplete();
  };

  const handleQuizComplete = () => {
    handleComplete();
    setShowQuiz(false);
  };

  return (
    <div className="max-w-4xl mx-auto">
      <div className="bg-white rounded-2xl shadow-sm border border-zinc-200 overflow-hidden">
        <div className="bg-gradient-to-r from-blue-500 to-blue-600 p-8 text-white">
          <h1 className="text-3xl font-bold mb-2">{lesson.title}</h1>
          <p className="text-blue-100">{lesson.description}</p>
          {isComplete && (
            <div className="mt-4 inline-flex items-center gap-2 bg-white/20 backdrop-blur-sm px-4 py-2 rounded-full text-sm font-medium">
              <span className="text-lg">✓</span> Completed
            </div>
          )}
        </div>

        <div className="p-8">
          <div className="prose prose-zinc max-w-none">
            {lesson.content.split('\n').map((line, idx) => {
              if (line.startsWith('# ')) {
                return <h1 key={idx} className="text-3xl font-bold mt-8 mb-4 text-zinc-900">{line.substring(2)}</h1>;
              } else if (line.startsWith('## ')) {
                return <h2 key={idx} className="text-2xl font-bold mt-6 mb-3 text-zinc-800">{line.substring(3)}</h2>;
              } else if (line.startsWith('### ')) {
                return <h3 key={idx} className="text-xl font-semibold mt-4 mb-2 text-zinc-700">{line.substring(4)}</h3>;
              } else if (line.startsWith('- ')) {
                return <li key={idx} className="ml-6 text-zinc-700 mb-1">{line.substring(2)}</li>;
              } else if (line.startsWith('**') && line.endsWith('**')) {
                return <p key={idx} className="font-bold text-zinc-900 mb-2">{line.replace(/\*\*/g, '')}</p>;
              } else if (line.trim() === '') {
                return <div key={idx} className="h-2" />;
              } else {
                return <p key={idx} className="text-zinc-700 mb-3 leading-relaxed">{line}</p>;
              }
            })}
          </div>

          {lesson.codeExample && (
            <div className="mt-8">
              <h3 className="text-xl font-semibold mb-4 text-zinc-900">Code Example</h3>
              <div className="bg-zinc-900 rounded-xl p-6 overflow-x-auto">
                <pre className="text-sm text-zinc-100 font-mono">
                  <code>{lesson.codeExample}</code>
                </pre>
              </div>
            </div>
          )}

          {lesson.quiz && lesson.quiz.length > 0 && (
            <div className="mt-8">
              {!showQuiz ? (
                <button
                  onClick={() => setShowQuiz(true)}
                  className="w-full bg-gradient-to-r from-blue-500 to-blue-600 text-white font-semibold py-4 px-6 rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all duration-200 shadow-lg hover:shadow-xl"
                >
                  Take Quiz to Complete Lesson
                </button>
              ) : (
                <Quiz 
                  questions={lesson.quiz} 
                  lessonId={lesson.id}
                  onComplete={handleQuizComplete}
                />
              )}
            </div>
          )}

          {(!lesson.quiz || lesson.quiz.length === 0) && !isComplete && (
            <button
              onClick={handleComplete}
              className="mt-8 w-full bg-gradient-to-r from-green-500 to-green-600 text-white font-semibold py-4 px-6 rounded-xl hover:from-green-600 hover:to-green-700 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              Mark as Complete
            </button>
          )}
        </div>
      </div>
    </div>
  );
}
