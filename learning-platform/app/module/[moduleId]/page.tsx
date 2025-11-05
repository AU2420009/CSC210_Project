"use client";

import { modules } from "@/data/modules";
import { useParams, useRouter } from "next/navigation";
import Link from "next/link";
import { useState, useEffect } from "react";
import { isLessonComplete } from "@/lib/progress";

export default function ModulePage() {
  const params = useParams();
  const router = useRouter();
  const moduleId = params.moduleId as string;
  
  const module = modules.find(m => m.id === moduleId);
  const [completedLessons, setCompletedLessons] = useState<Set<string>>(new Set());

  useEffect(() => {
    if (module) {
      const completed = new Set(
        module.lessons.filter(lesson => isLessonComplete(lesson.id)).map(l => l.id)
      );
      setCompletedLessons(completed);
    }
  }, [module]);

  if (!module) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-zinc-100 flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-zinc-900 mb-4">Module Not Found</h1>
          <Link href="/" className="text-blue-600 hover:text-blue-700 font-medium">
            ← Back to Home
          </Link>
        </div>
      </div>
    );
  }

  const progress = Math.round((completedLessons.size / module.lessons.length) * 100);

  return (
    <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-zinc-100">
      <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <Link 
          href="/" 
          className="inline-flex items-center text-blue-600 hover:text-blue-700 font-medium mb-8 transition-colors"
        >
          <span className="mr-2">←</span> Back to Modules
        </Link>

        <div className="bg-white rounded-2xl shadow-lg border border-zinc-200 overflow-hidden mb-8">
          <div className="bg-gradient-to-r from-blue-500 to-indigo-600 p-8 text-white">
            <div className="flex items-start justify-between">
              <div>
                <div className="text-5xl mb-4">{module.icon}</div>
                <h1 className="text-4xl font-bold mb-2">{module.title}</h1>
                <p className="text-blue-100 text-lg mb-4">{module.description}</p>
                <div className="flex items-center gap-4 text-sm">
                  <span className="bg-white/20 backdrop-blur-sm px-3 py-1 rounded-full">
                    {module.lessons.length} lessons
                  </span>
                  <span className="bg-white/20 backdrop-blur-sm px-3 py-1 rounded-full">
                    {module.estimatedTime}
                  </span>
                </div>
              </div>
            </div>

            <div className="mt-6">
              <div className="flex items-center justify-between text-sm mb-2">
                <span>Module Progress</span>
                <span className="font-semibold">{progress}%</span>
              </div>
              <div className="w-full bg-white/20 rounded-full h-3 overflow-hidden">
                <div 
                  className="bg-white h-full rounded-full transition-all duration-500"
                  style={{ width: `${progress}%` }}
                />
              </div>
            </div>
          </div>

          <div className="p-8">
            <h2 className="text-2xl font-bold text-zinc-900 mb-6">Lessons</h2>
            <div className="space-y-4">
              {module.lessons.map((lesson, index) => {
                const isComplete = completedLessons.has(lesson.id);
                
                return (
                  <Link
                    key={lesson.id}
                    href={`/module/${moduleId}/lesson/${lesson.id}`}
                    className="block group"
                  >
                    <div className="flex items-start gap-4 p-5 rounded-xl border-2 border-zinc-200 bg-white hover:border-blue-500 hover:shadow-lg transition-all duration-200">
                      <div className={`flex-shrink-0 w-10 h-10 rounded-full flex items-center justify-center font-bold text-lg ${
                        isComplete 
                          ? "bg-green-500 text-white" 
                          : "bg-zinc-100 text-zinc-600 group-hover:bg-blue-100 group-hover:text-blue-600"
                      }`}>
                        {isComplete ? "✓" : index + 1}
                      </div>
                      
                      <div className="flex-1 min-w-0">
                        <h3 className="text-lg font-semibold text-zinc-900 group-hover:text-blue-600 transition-colors mb-1">
                          {lesson.title}
                        </h3>
                        <p className="text-sm text-zinc-600 line-clamp-2">
                          {lesson.description}
                        </p>
                        {lesson.quiz && lesson.quiz.length > 0 && (
                          <div className="mt-2 inline-flex items-center gap-1 text-xs text-zinc-500">
                            <span>📝</span>
                            <span>{lesson.quiz.length} quiz questions</span>
                          </div>
                        )}
                      </div>

                      <div className="flex-shrink-0 text-blue-600 opacity-0 group-hover:opacity-100 transition-opacity">
                        →
                      </div>
                    </div>
                  </Link>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
