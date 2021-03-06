package org.sports.gate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.sports.ontology.model.DocumentModel;

public class DocumentTests {

	@Test
	public void TestQuoting() throws Exception {
		String content = "Григор Димитров е готов за участие на Откритото първенство на "
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
				+ "който е поставен под номер 10 в основната схема на Откритото първенство на Австралия.";

		DocumentModel document = new DocumentModel();
		document.setContent(content);
		document.setUrl("http://www.sportal.bg/news.php?news=519189");
		document.setDate(Calendar.getInstance().getTime());
		document.setKey("http://www.sportal.bg/news.php?news=519189");

		List<DocumentModel> documents = new ArrayList<DocumentModel>();
		documents.add(document);

		GateSportsApplication.annotate(documents);
	}

	@Test
	public void TestSampleResulting() throws Exception {
		String content = "Шампионът на Италия Ювентус постигна една от най-важните си победи от началото на сезона. "
				+ "Във второто голямо дерби от 18-ия кръг на Серия \"А\" \"бианконерите\" удариха Наполи с 3:1 като гост и постигнаха "
				+ "първи успех на \"Сан Паоло\" от 2000 година насам. По този начин Юве вече има аванс от 3 точки пре преследвача Рома, "
				+ "който днес завърши 2:2 с Лацио. Наполи пък окончателно се прости с надеждите си за титла, като изостава на 13 точки от "
				+ "\"старата госпожа\". Пол Погба откри за гостите с феноменален удар от въздуха в 29-ата минута, но в 64-ата Мигел Бритос "
				+ "изравни. Гостите отново излязоха напред с попадение на Мартин Касерес в 69-ата минута, при което имаше засада, а "
				+ "в добавеното време бомбен шут на Артуро Видал сложи точка на спора.Наполи - Ювентус 1:3+25 Преди Нова година "
				+ "неаполитанци изтръгнаха Суперкупата на Италия именно срещу \"бианконерите\". Сега шампионите бяха жадни за реванш и "
				+ "го постигнаха. Ювентус не успя да спечели 5 от предишните си 6 мача, включително и споменатия мач в Доха, в който "
				+ "Наполи триумфира след дузпи. Тогава и двата гола за Юве вкара Карлос Тевес, който и днес водеше атаката на тима, заедно "
				+ "с Фернандо Йоренте. От състава обаче отсъстваше Стефан Лихтщайнер, който не издържа късния фитнес тест. Вместо него "
				+ "започна Мартин Касерес. Уругваецът бе титуляр за пръв път от 3 месеца. В халфовата линия на \"старата госпожа\" си "
				+ "партнираха Пол Погба, Андреа Пирло и Клаудио Маркизио, а пред тях действаше Артуро Видал. Атаката на Наполи предвождаше "
				+ "Гонсало Игуаин, а зад него беше триото Хосе Кайехон, Марек Хамшик и Джонатан де Гузман. На пейката остана новото попънение "
				+ "Маноло Габиадини, привлечен от Сампдория. Давид Лопес и Валтер Гаргано бяха натоварени с дефанзивни функции в средата на терена. "
				+ "В защитата липсваше Фаузи Гулам, който е с националния отбор на Алжир на Купата на Африка. Вместо него ляв бек беше Мигел Бритос.Наполи - "
				+ "Ювентус 1:3+25 Наполи стартира много ентусиазирано, като Кайехон и Хамшик се стремяха да изострят събитията "
				+ "пред наказателното поле на гостите, но пък Игуаин оставаше изолиран. Първото голово положение дойде в 18-ата минута и бе пропиляно от "
				+ "Де Гузман. Той беше изведен на стрелкова позиция след чудесна комбинация на \"небесносините\", но шутира над напречната греда. "
				+ "Постепенно Ювентус изравни играта и все по-често Пирло и Погба успяваха да задържат топката. В 27-ата минута Хамшик центрира опасно, "
				+ "но Джанлуиджи Буфон внимаваше. Преди това пък Видал не успя да овладее хубаво подаване на Тевес. Малко след това Погба откри резултата "
				+ "с много красиво попадение. Французинът стреля от въздуха, посрещайки отбита топка, и направи безпомощен Рафаел Кабрал. Това бе трети гол "
				+ "за Погба в 5 мача срещу Наполи.Наполи - Ювентус 1:3+25 \"Бианконерите\" имаха чудесна възможност да удвоят преднината си малко преди "
				+ "почивката, но след като беше изведен по перфектен начин от Тевес, Мартин Касерес стреля право в ръцете на Кабрал. В началото на втората "
				+ "част домакините организираха бърза атака по десния фланг, но ударът на Кайехон беше избит в корнер. Юве отговори с коварно центриране към "
				+ "Погба и съвсем малко не достигна на французина, за да засече топката във вратата. Наполи - Ювентус 1:3+25 В тези минути над Неапол започна "
				+ "да се излива истински порой и това затрудни действията на футболистите. Домакините вдигнаха оборотите и атаките им станаха по-опасни след "
				+ "влизането в игра на Дрис Мертенс, който замени неубедителния Хамшик. В 64-ата минута белгиецът центрира от корнер, бранителите на Юве не се "
				+ "ориентираха и Бритос отблизо взриви трибуните - 1:1. Радостта на неаполитанци обаче не продължи дълго. Само пет минути по-късно Андреа Пирло "
				+ "центрира от фаул и Касерес се реваншира за пропуска си преди почивката, като вкара втори гол за Юве. Повторенията обаче показаха, че в момента "
				+ "на паса аржентинецът и Джорджо Киелини са в положение на засада. Веднага след гола Масимилиано Алегри смени Погба с Лихтщайнер. Юве се "
				+ "стремеше да убива темпото, а атаките на Наполи не носеха никаква изненада за отбраната в черно-бяло. Все пак, в края на двубоя домакините "
				+ "имаха две великолепни възможности да се доберта поне до точката. Първо резервата Дуван Сапата излезе очи в очи с Буфон и си поведе силно "
				+ "топката, като вместо да се опита да я догони и да отбележи, се строполи на терена. Реферът Паоло Талявенто не се хвана на симулацията на "
				+ "нападателя и му показа жълт картон. Секунди по-късно Игуаин майсторски се освободи в наказателното поле и стреля, но ударът му рикошира в "
				+ "бранител на Ювентус и това спаси гостите. В заключителните секунди Юве организра скоростна контра, при която Алваро Мората изведе Видал през "
				+ "центъра и той с мощен шут под гредата узакони така важната победа на шампионите.";

		DocumentModel document = new DocumentModel();
		document.setContent(content);
		document.setUrl("http://www.sportal.bg/news.php?news=523383");
		document.setDate(Calendar.getInstance().getTime());
		document.setKey("http://www.sportal.bg/news.php?news=523383");
		
		List<DocumentModel> documents = new ArrayList<DocumentModel>();
		documents.add(document);

		GateSportsApplication.annotate(documents);
	}
}
