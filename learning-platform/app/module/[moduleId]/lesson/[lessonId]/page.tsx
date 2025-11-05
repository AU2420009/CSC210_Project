"use client";

import { modules } from "@/data/modules";
import { useParams, useRouter } from "next/navigation";
import Link from "next/link";
import LessonViewer from "@/components/LessonViewer";

export default function LessonPage() {
  const params = useParams();
  const router = useRouter();
  const moduleId = params.moduleId as string;
  const lessonId = params.lessonId as string;
  
  const module = modules.find(m => m.id === moduleId);
  const lesson = module?.lessons.find(l => l.id === lessonId);
  const currentLessonIndex = module?.lessons.findIndex(l => l.id === lessonId) ?? -1;
  const nextLesson = module?.lessons[currentLessonIndex + 1];
  const prevLesson = currentLessonIndex > 0 ? module?.lessons[currentLessonIndex - 1] : null;

  if (!module || !lesson) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-zinc-100 flex items-center justify-center">
        <div className="text-center">
          <h1 className="text-4xl font-bold text-zinc-900 mb-4">Lesson Not Found</h1>
          <Link href="/" className="text-blue-600 hover:text-blue-700 font-medium">
            ← Back to Home
          </Link>
        </div>
      </div>
    );
  }

  const handleComplete = () => {
    if (nextLesson) {
      router.push(`/module/${moduleId}/lesson/${nextLesson.id}`);
    } else {
      router.push(`/module/${moduleId}`);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-zinc-100">
      <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="mb-8 flex items-center justify-between">
          <Link 
            href={`/module/${moduleId}`}
            className="inline-flex items-center text-blue-600 hover:text-blue-700 font-medium transition-colors"
          >
            <span className="mr-2">←</span> Back to {module.title}
          </Link>
          
          <div className="text-sm text-zinc-500">
            Lesson {currentLessonIndex + 1} of {module.lessons.length}
          </div>
        </div>

        <LessonViewer lesson={lesson} onComplete={handleComplete} />

        <div className="mt-8 flex items-center justify-between">
          {prevLesson ? (
            <Link
              href={`/module/${moduleId}/lesson/${prevLesson.id}`}
              className="inline-flex items-center gap-2 px-6 py-3 bg-white border-2 border-zinc-200 text-zinc-700 font-medium rounded-xl hover:border-blue-500 hover:text-blue-600 transition-all duration-200"
            >
              <span>←</span> Previous Lesson
            </Link>
          ) : (
            <div />
          )}

          {nextLesson ? (
            <Link
              href={`/module/${moduleId}/lesson/${nextLesson.id}`}
              className="inline-flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-blue-500 to-blue-600 text-white font-medium rounded-xl hover:from-blue-600 hover:to-blue-700 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              Next Lesson <span>→</span>
            </Link>
          ) : (
            <Link
              href={`/module/${moduleId}`}
              className="inline-flex items-center gap-2 px-6 py-3 bg-gradient-to-r from-green-500 to-green-600 text-white font-medium rounded-xl hover:from-green-600 hover:to-green-700 transition-all duration-200 shadow-lg hover:shadow-xl"
            >
              Complete Module <span>✓</span>
            </Link>
          )}
        </div>
      </div>
    </div>
  );
}
