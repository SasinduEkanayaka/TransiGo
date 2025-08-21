package com.transigo.app;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = TransiGoApplication.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface TransiGoApplication_GeneratedInjector {
  void injectTransiGoApplication(TransiGoApplication transiGoApplication);
}
