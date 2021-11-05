package model;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("로또 번호들")
class LottoNumbersTest {

	@Test
	@DisplayName("객체화")
	void instance() {
		assertThatNoException()
			.isThrownBy(() -> LottoNumbers.from(Collections.singleton(LottoNumber.from(1))));
	}

	@Test
	@DisplayName("로또 번호가 비어있는 상태에서 객체화하면 IllegalArgumentException")
	void instance_nullLottoPrice_thrownIllegalArgumentException() {
		assertThatIllegalArgumentException()
			.isThrownBy(() -> LottoNumbers.from(Collections.emptyList()))
			.withMessage("'collection' must not be empty");
	}

	@ParameterizedTest(name = "{displayName}[{index}] it is {1} that [1,2,3] contains {0}")
	@DisplayName("대상 번호 포함 여부")
	@CsvSource({"1,true", "10,false"})
	void contains(int number, boolean expected) {
		//given
		LottoNumbers numbers = LottoNumbers.from(lottoNumbers(1, 2, 3));

		//when, then
		assertThat(numbers.contains(LottoNumber.from(number)))
			.isEqualTo(expected);
	}

	@Test
	@DisplayName("대상 번호들 포함 갯수")
	void containsCount() {
		//given
		LottoNumbers numbers = LottoNumbers.from(lottoNumbers(1, 2, 3, 4, 5, 6));

		//when
		int count = numbers.containsCount(LottoNumbers.from(lottoNumbers(1, 2, 3, 8, 9, 10)));

		//then
		assertThat(count)
			.isEqualTo(3);
	}

	private Collection<LottoNumber> lottoNumbers(int... numbers) {
		Set<LottoNumber> numbersSet = new HashSet<>();
		for (int number : numbers) {
			numbersSet.add(LottoNumber.from(number));
		}
		return numbersSet;
	}
}