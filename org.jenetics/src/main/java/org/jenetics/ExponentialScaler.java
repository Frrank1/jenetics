/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics;

import static java.lang.String.format;
import static org.jenetics.util.object.eq;
import static org.jenetics.util.object.hashCodeOf;

import java.io.Serializable;

import javolution.lang.Immutable;

import org.jscience.mathematics.number.Float64;

import org.jenetics.util.Function;

/**
 * Implements an exponential fitness scaling, whereby all fitness values are
 * modified the following way.
 * <p/><img src="doc-files/exponential-scaler.gif"
 *          alt="f_s=\left(a\cdot f+b \rigth)^c"
 *     />.</p>
 *
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @since 1.0
 * @version 2.0 &mdash; <em>$Date: 2013-09-08 $</em>
 */
public final class ExponentialScaler
	implements
		Function<Float64, Float64>,
		Serializable,
		Immutable
{
	private static final long serialVersionUID = 1L;

	public static final ExponentialScaler SQR_SCALER = new ExponentialScaler(2);
	public static final ExponentialScaler SQRT_SCALER = new ExponentialScaler(0.5);

	private final double _a;
	private final double _b;
	private final double _c;

	/**
	 * Create a new FitnessScaler.
	 *
	 * @param a <pre>fitness = (<strong>a</strong> * fitness + b) ^ c</pre>
	 * @param b <pre>fitness = (a * fitness + <strong>b</strong>) ^ c</pre>
	 * @param c <pre>fitness = (a * fitness + b) ^ <strong>c</strong></pre>
	 */
	public ExponentialScaler(final double a, final double b, final double c) {
		_a = a;
		_b = b;
		_c = c;
	}

	/**
	 * Create a new FitnessScaler.
	 *
	 * @param b <pre>fitness = (1 * fitness + <strong>b</strong>) ^ c</pre>
	 * @param c <pre>fitness = (1 * fitness + b) ^ <strong>c</strong></pre>
	 */
	public ExponentialScaler(final double b, final double c) {
		this(1.0, b, c);
	}

	/**
	 * Create a new FitnessScaler.
	 *
	 * @param c <pre>fitness = (1 * fitness + 0) ^ <strong>c</strong></pre>
	 */
	public ExponentialScaler(final double c) {
		this(0.0, c);
	}


	@Override
	public Float64 apply(final Float64 value) {
		return Float64.valueOf(Math.pow((_a*value.doubleValue() + _b), _c));
	}

	@Override
	public int hashCode() {
		return hashCodeOf(getClass()).and(_a).and(_b).and(_c).value();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}

		final ExponentialScaler selector = (ExponentialScaler)obj;
		return eq(_a, selector._a) && eq(_b, selector._b) && eq(_c, selector._c);
	}

	@Override
	public String toString() {
		return format(
			"%s[a=%f, b=%f, c=%f]",
			getClass().getSimpleName(), _a, _b, _c
		);
	}
}
