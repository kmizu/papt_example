package com.github.kmizu.papt_example.apt;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("com.github.kmizu.papt_example.EntryPoint")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class EntryPointProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        var messager = super.processingEnv.getMessager();

        for(TypeElement annotation : annotations) {
            for(Element element:env.getElementsAnnotatedWith(annotation)) {
                ExecutableElement eElement = (ExecutableElement)element;
                if(!eElement.getSimpleName().contentEquals("main")){
                    messager.printMessage(Diagnostic.Kind.ERROR, "The name of @EntryPoint method must be `main`", eElement);
                    return false;
                }
                if(eElement.getReturnType().getKind().compareTo(TypeKind.VOID) != 0) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "The return type of EntryPoint method must be void", eElement);
                    return false;
                }
                var parameters = eElement.getParameters();
                if(parameters.size() != 1) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "The parameter length of EntryPoint method must be 1", eElement);
                    return false;
                }
                var mainArg = parameters.get(0);
                if(!mainArg.asType().toString().equals("java.lang.String[]")) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "The parameter type of EntryPoint method must be String[]", eElement);
                    return false;
                }
            }
        }
        return true;
    }
}