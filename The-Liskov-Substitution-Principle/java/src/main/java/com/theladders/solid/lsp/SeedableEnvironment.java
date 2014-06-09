package com.theladders.solid.lsp;

public interface SeedableEnvironment
{
  void copy(Object fromKey,
            Object toKey);

  void copyAndAppend(Object fromKey,
                     Object toKey,
                     Object toAppend);

}
