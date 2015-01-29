package org.sports.gate;

import org.junit.Test;

public class DocumentTests {

	@Test
	public void TestQuoting() throws Exception {
		String[] documents = { "Григор Димитров е готов за участие на Откритото първенство на "
				+ "Австралия по тенис. Поставеният под номер 10 българин се чувства отлично преди старта на "
				+ "мачовете в Мелбърн, като негов съперник в първия кръг е Дъстин Браун (Германия). "
				+ "\"Свърших много работа през паузата. Знаехме какво работи добре и върху какво трябва да се"
				+ " наблегне. Работих усилено преди сезона, на корта и извън него. Никога не съм искал да си "
				+ "поставям твърде много задачи. Представянето ми миналата година тук е от основно значение "
				+ "за мен. Като цяло 2014 година беше доста добра. Всичко това дава доста голямо самочувствие, "
				+ "което дава шанс да се представям още по-силно. То помага да побеждаваш по-силните тенисисти. "
				+ "Спечелих няколко турнира и това е доста добре. Миналогодишната загуба от Рафаел Надал я приех "
				+ "като победа, въпреки че ми отне доста време да се съвзема от нея. Имаше и положителни неща от "
				+ "това поражение. В Мелбърн цялостната обстановка те кара да дадеш всичко от себе си. Винаги "
				+ "е удоволствие да играеш тук\", заяви Григор Димитров на пресконференция. Той говори и за "
				+ "турнира в Бризбън, където миналата седмица загуби на полуфиналите от бившия номер 1 в "
				+ "световната ранглиста Роджър Федерер. \"Това беше труден мач и не се представих по начина, "
				+ "по който исках. Но това не означава, че съм разколебан. Сезонът тъкмо започва. Стартира и Откритото "
				+ "първенство в Австралия, всичко може да се случи\", каза още Димитров. Младият български тенисист говори "
				+ "добре английски, разбира френски, но в определени ситуации използва и руския език. \"Говоря български и "
				+ "английски. Разбирам френски. Говоря руски, когато трябва. Учил съм го, но има кой да ми помага. "
				+ "Придържам се към английския, но разбирам и малко френски\", добави Григор Димитров, "
				+ "който е поставен под номер 10 в основната схема на Откритото първенство на Австралия." };

		Application.main(documents);
	}
}