/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prabhu.jeazyprops.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author User
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface PropsElement {

    public String displayName() default "##default";

    public boolean isEncrypted() default false;

    public String keyName() default "##default";

    public String[] groups() default "";

}
