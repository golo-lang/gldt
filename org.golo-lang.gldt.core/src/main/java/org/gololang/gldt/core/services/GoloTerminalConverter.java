/*******************************************************************************
* Copyright 2013 Jeff MAURY
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
******************************************************************************/
package org.gololang.gldt.core.services;

import javax.inject.Singleton;

import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;

/**
 *
 */
@Singleton
public class GoloTerminalConverter extends DefaultTerminalConverters {
	
	
  private class IntConverter extends AbstractLexerBasedConverter<Integer> {

    public Integer toValue(String string, INode node)
        throws ValueConverterException {
      return Integer.parseInt(string.replace("_", "")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    protected String toEscapedString(Integer value) {
      return value.toString();
    }
  }
  
  private final IValueConverter<Integer> GNUMBER = new IntConverter();
  
  @ValueConverter(rule = "GNUMBER")
  public IValueConverter<Integer> GNUMBER() {
    return GNUMBER;
  }


  private class LongConverter extends AbstractLexerBasedConverter<Long> {

		private static final String LONG_SUFFIX = "_L"; //$NON-NLS-1$
		
		public Long toValue(String string, INode node)
				throws ValueConverterException {
			if (string.endsWith(LONG_SUFFIX)) {
				string = string.substring(0, string.length() - LONG_SUFFIX.length());
			}
      string = string.replace("_", "");  //$NON-NLS-1$//$NON-NLS-2$
			return Long.parseLong(string);
		}

		@Override
		protected String toEscapedString(Long value) {
			return value.toString() + LONG_SUFFIX;
		}
	}
	
	private final IValueConverter<Long> LONG_NUMBER = new LongConverter();
	
	@ValueConverter(rule = "LONG_NUMBER")
	public IValueConverter<Long> LONG_NUMBER() {
		return LONG_NUMBER;
	}
	
	private class DoubleConverter extends AbstractLexerBasedConverter<Double> {

		public Double toValue(String string, INode node)
				throws ValueConverterException {
			return Double.parseDouble(string);
		}
	}
	
	private final IValueConverter<Double> FLOATING_NUMBER = new DoubleConverter();
	
	@ValueConverter(rule = "FLOATING_NUMBER")
	public IValueConverter<Double> FLOATING_NUMBER() {
		return FLOATING_NUMBER;
	}
	
	private class FloatConverter extends AbstractLexerBasedConverter<Float> {

		private static final String FLOAT_SUFFIX = "_F"; //$NON-NLS-1$

		public Float toValue(String string, INode node)
				throws ValueConverterException {
			if (string.endsWith(FLOAT_SUFFIX)) {
				string = string.substring(0, string.length() - FLOAT_SUFFIX.length());
			}

			return Float.parseFloat(string);
		}
		
		@Override
		protected String toEscapedString(Float value) {
			return value.toString() + FLOAT_SUFFIX;
		}

	}
	
	private final IValueConverter<Float> FLOAT = new FloatConverter();
	
	@ValueConverter(rule = "FLOAT")
	public IValueConverter<Float> FLOAT() {
		return FLOAT;
	}
}
