package com.github.kmizu.papt_example.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;

import java.util.Set;

@SupportedAnnotationTypes("com.github.kmizu.papt_example.Hello")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class HelloProcessor extends AbstractProcessor {
    private int round = 0;
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        // case round == 0 is executed surely
        if(round == 0) {
            round ++;
            return true;
        }
        var messager = super.processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING, "Hello, Annotation!");
        System.out.println("FOO");
        return true;
    }
}