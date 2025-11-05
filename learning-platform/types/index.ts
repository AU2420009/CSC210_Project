export interface QuizQuestion {
  id: string;
  question: string;
  options: string[];
  correctAnswer: number;
  explanation: string;
}

export interface Lesson {
  id: string;
  title: string;
  description: string;
  content: string;
  codeExample?: string;
  quiz?: QuizQuestion[];
}

export interface Module {
  id: string;
  title: string;
  description: string;
  icon: string;
  lessons: Lesson[];
  estimatedTime: string;
}

export interface UserProgress {
  completedLessons: string[];
  quizScores: Record<string, number>;
  currentModule?: string;
  currentLesson?: string;
}
