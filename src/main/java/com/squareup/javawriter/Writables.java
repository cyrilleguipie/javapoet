/*
 * Copyright (C) 2014 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.javawriter;

import com.squareup.javawriter.Writable.Context;
import java.io.IOException;
import java.util.Set;

final class Writables {
  static Writable toStringWritable(final Object object) {
    return new Writable() {
      @Override
      public Appendable write(Appendable appendable, Context context) throws IOException {
        return appendable.append(object.toString());
      }
    };
  }

  private static final Context DEFAULT_CONTEXT = new Context() {
    @Override
    public String sourceReferenceForClassName(ClassName className) {
      return className.canonicalName();
    }

    @Override
    public Context createSubcontext(Set<ClassName> newTypes) {
      throw new UnsupportedOperationException();
    }
  };

  static String writeToString(Writable writable) {
    StringBuilder builder = new StringBuilder();
    try {
      writable.write(builder, DEFAULT_CONTEXT);
    } catch (IOException e) {
      throw new AssertionError("StringBuilder doesn't throw IOException" + e);
    }
    return builder.toString();
  }

  private Writables() {
  }
}