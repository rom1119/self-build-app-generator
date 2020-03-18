package com.SelfBuildApp.ddd.Project.domain.CodeGenerator;

public interface CodeGenerator<T> {

    CodeGeneratedItem generate(T arg);
}
