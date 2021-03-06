package eu.kanade.tachiyomi.extension.all.madara

import android.annotation.SuppressLint
import eu.kanade.tachiyomi.annotations.Nsfw
import eu.kanade.tachiyomi.network.GET
import eu.kanade.tachiyomi.network.POST
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.SourceFactory
import eu.kanade.tachiyomi.source.model.Filter
import eu.kanade.tachiyomi.source.model.FilterList
import eu.kanade.tachiyomi.source.model.Page
import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.util.asJsoup
import okhttp3.CacheControl
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

class MadaraFactory : SourceFactory {
    override fun createSources(): List<Source> = listOf(
        ATMSubs(),
        AdonisFansub(),
        AkuManga(),
        AllPornComic(),
        AoCTranslations(),
        ArangScans(),
        ArazNovel(),
        AsgardTeam(),
        AstralLibrary(),
        Azora(),
        Bakaman(),
        BestManga(),
        BoysLove(),
        CatTranslator(),
        ChibiManga(),
        ComicKiba(),
        ComicsValley(),
        CopyPasteScan(),
        DarkyuRealm(),
        DecadenceScans(),
        DetectiveConanAr(),
        DisasterScans(),
        DoujinHentai(),
        DoujinYosh(),
        DropeScan(),
        EinherjarScan(),
        FirstKissManga(),
        FirstKissManhua(),
        FriendlyTranslations(),
        FunList(),
        FurioScans(),
        GoldenManga(),
        GuncelManga(),
        HeroManhua(),
        Hiperdex(),
        HunterFansub(),
        IchirinNoHanaYuri(),
        ImmortalUpdates(),
        IsekaiScanCom(),
        JustForFun(),
        JJutsuScans(),
        KMangaIn(),
        KingzManga(),
        KlanKomik(),
        KlikManga(),
        KomikGo(),
        LilyManga(),
        LuxyScans(),
        Manga347(),
        Manga3asq(),
        Manga68(),
        MangaAction(),
        MangaArabTeam(),
        MangaBob(),
        MangaDods(),
        MangaHentai(),
        MangaKiss(),
        MangaKomi(),
        MangaLord(),
        MangaLandArabic(),
        MangaPhoenix(),
        MangaRawr(),
        MangaRead(),
        MangaReadOrg(),
        MangaRockTeam(),
        MangaRocky(),
        MangaScantrad(),
        MangaStream(),
        MangaSY(),
        MangaTX(),
        MangaTurf(),
        MangaWT(),
        MangaYosh(),
        Mangalek(),
        Mangareceh(),
        Mangasushi(),
        MangazukiClubJP(),
        MangazukiClubKO(),
        MangazukiMe(),
        MangazukiOnline(),
        ManhuaBox(),
        ManhuaPlus(),
        ManhuaUS(),
        ManhuasWorld(),
        Manhuasnet(),
        ManhwaRaw(),
        ManhwaTop(),
        ManwahentaiMe(),
        ManwhaClub(),
        ManyToon(),
        ManyToonClub(),
        ManyToonMe(),
        MartialScans(),
        Milftoon(),
        MiracleScans(),
        MixedManga(),
        NeoxScanlator(),
        NightComic(),
        NijiTranslations(),
        NinjaScans(),
        Ninjavi(),
        OnManga(),
        OrigamiOrpheans(),
        PMScans(),
        PojokManga(),
        PornComix(),
        QueensManga(),
        RaiderScans(),
        ReadManhua(),
        RenaScans(),
        S2Manga(),
        SheaManga(),
        Skymanga(),
        TheTopComic(),
        ThreeSixtyFiveManga(),
        ToonPoint(),
        Toonily(),
        ToonilyNet(),
        TopManhua(),
        TritiniaScans(),
        TruyenTranhAudioCom(),
        TruyenTranhAudioOnline(),
        TsubakiNoScan(),
        TurkceManga(),
        TwilightScans(),
        Wakamics(),
        Wakascan(),
        WeScans(),
        WebNovel(),
        WebtoonXYZ(),
        WoopRead(),
        WordExcerpt(),
        WuxiaWorld(),
        YaoiToshokan(),
        YokaiJump(),
        ZManga(),
        ZinManga(),
        ShoujoHearts(),
        AlianzaMarcial(),
        OlaoeManga(),
        FdmScan(),
        ArgosScan(),
        Kombatch(),
        ProjetoScanlator(),
        HikariScan()
        // Removed by request of site owner
        // EarlyManga(),
        // MangaGecesi(),
        // MangaWOW(),
        // MangaStein(),
        // KnightNoScanlation(),
        // AhStudios(),
    )
}

class TritiniaScans : Madara("TritiniaScans", "https://tritiniaman.ga", "en") {
    // site is a bit broken
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga/index_m_orderby=views.html", headers)
    override fun popularMangaNextPageSelector(): String? = null
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga/index_m_orderby=latest.html", headers)
    override fun latestUpdatesNextPageSelector(): String? = null
    private val imageRegex = Regex(""""(http[^"]*)"""")
    override fun pageListParse(document: Document): List<Page> {
        val script = document.select("#chapter_preloaded_images").firstOrNull()?.data()
            ?: throw Exception("chapter_preloaded_images not found")
        return imageRegex.findAll(script).asIterable().mapIndexed { i, mr ->
            Page(i, "", mr.groupValues[1].replace("\\", ""))
        }
    }
}

class CopyPasteScan : Madara("CopyPasteScan", "https://copypastescan.xyz", "es")

class FunList : Madara("FunList", "https://funlist.online", "en")

class Mangasushi : Madara("Mangasushi", "https://mangasushi.net", "en")

class MangaRawr : Madara("MangaRawr", "https://mangarawr.com", "en")

class NinjaScans : Madara("NinjaScans", "https://ninjascans.com", "en")

class ReadManhua : Madara(
    "ReadManhua",
    "https://readmanhua.net",
    "en",
    dateFormat = SimpleDateFormat("dd MMM yy", Locale.US)
)

class IsekaiScanCom : Madara("IsekaiScan.com", "https://isekaiscan.com", "en")

class JustForFun : Madara(
    "Just For Fun",
    "https://just-for-fun.ru",
    "ru",
    dateFormat = SimpleDateFormat("yy.MM.dd", Locale.US)
)

class AoCTranslations : Madara("Agent of Change Translations", "https://aoc.moe", "en") {
    override fun headersBuilder(): Headers.Builder = super.headersBuilder().add("Referer", baseUrl)
    override fun popularMangaSelector() = "div.page-item-detail.manga:has(span.chapter)"
    override fun chapterListSelector() = "li.wp-manga-chapter:has(a)"
    @SuppressLint("DefaultLocale")
    override fun chapterListParse(response: Response): List<SChapter> {
        return response.asJsoup().let { document ->
            document.select(chapterListSelector()).let { normalChapters ->
                if (normalChapters.isNotEmpty()) {
                    normalChapters.map { chapterFromElement(it) }
                } else {
                    // For their "fancy" volume/chapter lists
                    document.select("div.wpb_wrapper:contains(volume) a")
                        .filter { it.attr("href").contains(baseUrl) && !it.attr("href").contains("imgur") }
                        .map { volumeChapter ->
                            SChapter.create().apply {
                                volumeChapter.attr("href").let { url ->
                                    name = if (url.contains("volume")) {
                                        val volume = url.substringAfter("volume-").substringBefore("/")
                                        val volChap = url.substringAfter("volume-$volume/").substringBefore("/").replace("-", " ").capitalize()
                                        "Volume $volume - $volChap"
                                    } else {
                                        url.substringBefore("/p").substringAfterLast("/").replace("-", " ").capitalize()
                                    }
                                    setUrlWithoutDomain(url.substringBefore("?") + "?style=list")
                                }
                            }
                        }
                }.reversed()
            }
        }
    }
}

class KomikGo : Madara("KomikGo", "https://komikgo.com", "id")

class LuxyScans : Madara("Luxy Scans", "https://luxyscans.com", "en")

class TsubakiNoScan : Madara(
    "Tsubaki No Scan",
    "https://tsubakinoscan.com",
    "fr",
    dateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)
)

class YokaiJump : Madara(
    "Yokai Jump",
    "https://yokaijump.fr",
    "fr",
    dateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)
)

class ZManga : Madara("ZManga", "https://zmanga.org", "es")

class MangazukiMe : Madara("Mangazuki.me", "https://mangazuki.me", "en")

class MangazukiOnline : Madara("Mangazuki.online", "https://www.mangazuki.online", "en") {
    override val client: OkHttpClient = super.client.newBuilder().followRedirects(true).build()
}

class MangazukiClubJP : Madara("Mangazuki.club", "https://mangazuki.club", "ja")

class MangazukiClubKO : Madara("Mangazuki.club", "https://mangazuki.club", "ko")

class FirstKissManga : Madara(
    "1st Kiss",
    "https://1stkissmanga.com",
    "en",
    dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
) {
    override fun headersBuilder(): Headers.Builder = super.headersBuilder().add("Referer", baseUrl)
}

class MangaSY : Madara("Manga SY", "https://www.mangasy.com", "en") {
    override fun imageRequest(page: Page): Request = super.imageRequest(page).newBuilder()
        .cacheControl(CacheControl.FORCE_NETWORK)
        .build()
}

class ManwhaClub : Madara("Manwha Club", "https://manhwa.club", "en")

class WuxiaWorld : Madara("WuxiaWorld", "https://wuxiaworld.site", "en") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/tag/webcomic/page/$page/?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/tag/webcomic/page/$page/?m_orderby=latest", headers)
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList) = super.searchMangaRequest(page, "$query comics", filters)
    override fun popularMangaNextPageSelector() = "div.nav-previous.float-left"
}

class ManyToon : Madara("ManyToon", "https://manytoon.com", "en")

class ManyToonMe : Madara("ManyToon.me", "https://manytoon.me", "en")

class BoysLove : Madara("BoysLove", "https://boyslove.me", "en")

class ChibiManga : Madara(
    "Chibi Manga",
    "http://www.cmreader.info",
    "en",
    dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
) {
    override fun chapterListParse(response: Response): List<SChapter> {
        response.asJsoup().let { documet ->
            documet.select("li.parent.has-child").let { volumes ->
                return if (volumes.isNullOrEmpty()) {
                    documet.select(chapterListSelector()).map { chapterFromElement(it) }
                } else {
                    val chapters = mutableListOf<SChapter>()
                    volumes.reversed().forEach { v ->
                        val vName = v.select("a[href^=javascript]").text()
                        v.select(chapterListSelector()).map { chapters.add(chapterFromElement(it).apply { name = "$vName - $name" }) }
                    }
                    chapters
                }
            }
        }
    }
}

class ZinManga : Madara("Zin Translator", "https://zinmanga.com", "en") {
    override fun headersBuilder(): Headers.Builder = super.headersBuilder()
        .add("Referer", "https://zinmanga.com/")
}

class ManwahentaiMe : Madara("Manwahentai.me", "https://manhwahentai.me", "en")

class Manga3asq : Madara("مانجا العاشق", "https://3asq.org", "ar")

class AdonisFansub : Madara("Adonis Fansub", "https://manga.adonisfansub.com", "tr") {
    override val userAgentRandomizer = ""
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=latest", headers)
}

@Nsfw
class AllPornComic : Madara("AllPornComic", "https://allporncomic.com", "en") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=latest", headers)
    override fun searchMangaNextPageSelector() = "a[rel=next]"
    override fun getGenreList() = listOf(
        Genre("3D", "3d"),
        Genre("Ahegao", "ahegao"),
        Genre("Alien Girl", "alien-girl"),
        Genre("Anal", "anal"),
        Genre("Anime", "anime"),
        Genre("Anthology", "anthology"),
        Genre("Artbook", "artbook"),
        Genre("BBW / Chubby / Fat Woman", "bbw"),
        Genre("BDSM", "bdsm"),
        Genre("Big Areolae", "big-areolae"),
        Genre("Big Ass", "big-ass"),
        Genre("Big Balls", "big-balls"),
        Genre("Big Breasts", "big-breasts"),
        Genre("Big Clit", "big-clit"),
        Genre("Big Nipples", "big-nipples"),
        Genre("Big Penis", "big-penis"),
        Genre("Bikini", "bikini"),
        Genre("Blackmail", "blackmail"),
        Genre("Blindfold", "blindfold"),
        Genre("Body Modification", "body-modification"),
        Genre("Body Swap", "body-swap"),
        Genre("Body Writing", "body-writing"),
        Genre("BodyStocking", "bodystocking"),
        Genre("Bodysuit", "bodysuit"),
        Genre("Bondage", "bondage"),
        Genre("Brain Fuck", "brain-fuck"),
        Genre("Cartoon", "cartoon"),
        Genre("Cheerleader", "cheerleader"),
        Genre("Chinese Dress", "chinese-dress"),
        Genre("Collar / Choker", "collar"),
        Genre("Comedy", "comedy"),
        Genre("Corruption", "corruption"),
        Genre("Corset", "corset"),
        Genre("Crotch Tattoo", "crotch-tattoo"),
        Genre("Dark Skin", "dark-skin"),
        Genre("Demon Girl / Succubus", "demon-girl"),
        Genre("Dick Growth", "dick-growth"),
        Genre("Dickgirl On Dickgirl", "dickgirl-on-dickgirl"),
        Genre("Dickgirl On Male", "dickgirl-on-male"),
        Genre("Dickgirls Only", "dickgirls-only"),
        Genre("Drugs", "drugs"),
        Genre("Drunk", "drunk"),
        Genre("Exhibitionism", "exhibitionism"),
        Genre("FFM Threesome", "ffm-threesome"),
        Genre("FFT Threesome", "fft-threesome"),
        Genre("Females Only", "females-only"),
        Genre("Femdom", "femdom"),
        Genre("Feminization", "feminization"),
        Genre("Full Body Tattoo", "full-body-tattoo"),
        Genre("Full Color", "full-color"),
        Genre("Futanari", "futanari"),
        Genre("Gender Bender", "gender-bender"),
        Genre("Glasses", "glasses"),
        Genre("Group", "group"),
        Genre("Gyaru", "gyaru"),
        Genre("Gyaru-OH", "gyaru-oh"),
        Genre("Harem", "harem"),
        Genre("Hentai", "hentai"),
        Genre("Human Pet", "human-pet"),
        Genre("Humiliation", "humiliation"),
        Genre("Impregnation", "impregnation"),
        Genre("Incest", "incest"),
        Genre("Interracial", "interracial"),
        Genre("Kimono", "kimono"),
        Genre("Latex", "latex"),
        Genre("Leash", "leash"),
        Genre("Lingerie", "lingerie"),
        Genre("Lolicon", "lolicon"),
        Genre("MILF", "milf"),
        Genre("MMF Threesome", "mmf-threesome"),
        Genre("MMT Threesome", "mmt-threesome"),
        Genre("Magical Girl", "magical-girl"),
        Genre("Maid", "maid"),
        Genre("Male On Dickgirl", "male-on-dickgirl"),
        Genre("Manhwa", "manhwa"),
        Genre("Military", "military"),
        Genre("Milking", "milking"),
        Genre("Mind Break", "mind-break"),
        Genre("Mind Control", "mind-control"),
        Genre("Monster Girl", "monster-girl"),
        Genre("Moral Degeneration", "moral-degeneration"),
        Genre("Muscle", "muscle"),
        Genre("Muscle Growth", "muscle-growth"),
        Genre("Nakadashi", "nakadashi"),
        Genre("Netorare", "netorare"),
        Genre("Netori", "netori"),
        Genre("Ninja", "ninja"),
        Genre("Nun", "nun"),
        Genre("Nurse", "nurse"),
        Genre("Orgy", "orgy"),
        Genre("Paizuri", "paizuri"),
        Genre("Pegging", "pegging"),
        Genre("Piercing", "piercing"),
        Genre("Pixie Cut", "pixie-cut"),
        Genre("Policewoman", "policewoman"),
        Genre("Possession", "possession"),
        Genre("Retro", "retro"),
        Genre("Ryona", "ryona"),
        Genre("School Swimsuit", "school-swimsuit"),
        Genre("Schoolboy Uniform", "schoolboy-uniform"),
        Genre("Schoolgirl Uniform", "schoolgirl-uniform"),
        Genre("Shared Senses", "shared-senses"),
        Genre("Shemale", "shemale"),
        Genre("Shibari", "shibari"),
        Genre("Shotacon", "shotacon"),
        Genre("Slave", "slave"),
        Genre("Slime Girl", "slime-girl"),
        Genre("Small Breasts", "small-breasts"),
        Genre("Stockings", "stockings"),
        Genre("Strap-on", "strap-on"),
        Genre("Stuck In Wall", "stuck-in-wall"),
        Genre("Superhero", "superhero"),
        Genre("Superheroine", "superheroine"),
        Genre("Tail", "tail"),
        Genre("Tail Plug", "tail-plug"),
        Genre("Tankoubon", "tankoubon"),
        Genre("Tentacles", "tentacles"),
        Genre("Thigh High Boots", "thigh-high-boots"),
        Genre("Tights", "tights"),
        Genre("Time Stop", "time-stop"),
        Genre("Tomboy", "tomboy"),
        Genre("Tomgirl", "tomgirl"),
        Genre("Torture", "torture"),
        Genre("Transformation", "transformation"),
        Genre("Uncensored", "uncensored"),
        Genre("Unusual Pupils", "unusual-pupils"),
        Genre("Unusual Teeth", "unusual-teeth"),
        Genre("Vampire", "vampire"),
        Genre("Virginity", "virginity"),
        Genre("Voyeurism", "voyeurism"),
        Genre("Webtoon", "webtoon"),
        Genre("Western", "western"),
        Genre("Witch", "witch"),
        Genre("Yandere", "yandere"),
        Genre("Yaoi", "yaoi"),
        Genre("Yuri", "yuri")
    )
}

class Milftoon : Madara("Milftoon", "https://milftoon.xxx", "en") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/page/$page/?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/page/$page/?m_orderby=latest", headers)
}

class Hiperdex : Madara("Hiperdex", "https://hiperdex.com", "en") {
    override fun getGenreList() = listOf(
        Genre("Adult", "adult"),
        Genre("Action", "action"),
        Genre("Adventure", "adventure"),
        Genre("Bully", "bully"),
        Genre("Comedy", "comedy"),
        Genre("Drama", "drama"),
        Genre("Ecchi", "ecchi"),
        Genre("Fantasy", "fantasy"),
        Genre("Gender Bender", "gender-bender"),
        Genre("Harem", "harem"),
        Genre("Historical", "historical"),
        Genre("Horror", "horror"),
        Genre("Isekai", "isekai"),
        Genre("Josei", "josei"),
        Genre("Martial Arts", "martial-arts"),
        Genre("Mature", "mature"),
        Genre("Mystery", "mystery"),
        Genre("Psychological", "psychological"),
        Genre("Romance", "romance"),
        Genre("School Life", "school-life"),
        Genre("Sci-Fi", "sci-fi"),
        Genre("Seinen", "seinen"),
        Genre("Shoujo", "shoujo"),
        Genre("Shounen", "shounen"),
        Genre("Slice of Life", "slice-of-life"),
        Genre("Smut", "smut"),
        Genre("Sports", "sports"),
        Genre("Supernatural", "supernatural"),
        Genre("Thriller", "thriller"),
        Genre("Tragedy", "tragedy"),
        Genre("Yaoi", "yaoi"),
        Genre("Yuri", "yuri")
    )
}

@Nsfw
class DoujinHentai : Madara("DoujinHentai", "https://doujinhentai.net", "es", SimpleDateFormat("d MMM. yyyy", Locale.ENGLISH)) {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/lista-manga-hentai?orderby=views&page=$page", headers)
    override fun popularMangaSelector() = "div.col-md-3 a"
    override fun popularMangaFromElement(element: Element): SManga {
        val manga = SManga.create()

        manga.setUrlWithoutDomain(element.attr("href"))
        manga.title = element.select("h5").text()
        manga.thumbnail_url = element.select("img").attr("abs:data-src")

        return manga
    }

    override fun popularMangaNextPageSelector() = "a[rel=next]"
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/lista-manga-hentai?orderby=last&page=$page", headers)
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request {
        val url = HttpUrl.parse(baseUrl)!!.newBuilder()
        if (query.isNotBlank()) {
            url.addPathSegment("search")
            url.addQueryParameter("query", query) // query returns results all on one page
        } else {
            filters.forEach { filter ->
                when (filter) {
                    is GenreSelectFilter -> {
                        if (filter.state != 0) {
                            url.addPathSegments("lista-manga-hentai/category/${filter.toUriPart()}")
                            url.addQueryParameter("page", page.toString())
                        }
                    }
                }
            }
        }
        return GET(url.build().toString(), headers)
    }

    override fun searchMangaSelector() = "div.c-tabs-item__content > div.c-tabs-item__content, ${popularMangaSelector()}"
    override fun searchMangaFromElement(element: Element): SManga {
        return if (element.hasAttr("href")) {
            popularMangaFromElement(element) // genre search results
        } else {
            super.searchMangaFromElement(element) // query search results
        }
    }

    override fun searchMangaNextPageSelector() = popularMangaNextPageSelector()
    override fun chapterListSelector() = "ul.main.version-chap > li.wp-manga-chapter:not(:last-child)" // removing empty li
    override val pageListParseSelector = "div#all > img.img-responsive"
    override fun getFilterList() = FilterList(
        Filter.Header("Solo funciona si la consulta está en blanco"),
        GenreSelectFilter()
    )

    class GenreSelectFilter : UriPartFilter(
        "Búsqueda de género",
        arrayOf(
            Pair("<seleccionar>", ""),
            Pair("Ecchi", "ecchi"),
            Pair("Yaoi", "yaoi"),
            Pair("Yuri", "yuri"),
            Pair("Anal", "anal"),
            Pair("Tetonas", "tetonas"),
            Pair("Escolares", "escolares"),
            Pair("Incesto", "incesto"),
            Pair("Virgenes", "virgenes"),
            Pair("Masturbacion", "masturbacion"),
            Pair("Maduras", "maduras"),
            Pair("Lolicon", "lolicon"),
            Pair("Bikini", "bikini"),
            Pair("Sirvientas", "sirvientas"),
            Pair("Enfermera", "enfermera"),
            Pair("Embarazada", "embarazada"),
            Pair("Ahegao", "ahegao"),
            Pair("Casadas", "casadas"),
            Pair("Chica Con Pene", "chica-con-pene"),
            Pair("Juguetes Sexuales", "juguetes-sexuales"),
            Pair("Orgias", "orgias"),
            Pair("Harem", "harem"),
            Pair("Romance", "romance"),
            Pair("Profesores", "profesores"),
            Pair("Tentaculos", "tentaculos"),
            Pair("Mamadas", "mamadas"),
            Pair("Shota", "shota"),
            Pair("Interracial", "interracial"),
            Pair("Full Color", "full-colo"),
            Pair("Sin Censura", "sin-censura"),
            Pair("Futanari", "futanari"),
            Pair("Doble Penetracion", "doble-penetracion"),
            Pair("Cosplay", "cosplay")
        )
    )
}

class Azora : Madara("Azora", "https://www.azoramanga.com", "ar") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/page/$page/?m_orderby=views", headers)
    override fun chapterListSelector() = "li.wp-manga-chapter:not(.premium-block)" // Filter fake chapters
    override fun chapterFromElement(element: Element): SChapter {
        val chapter = SChapter.create()

        element.select("a").let {
            chapter.url = it.attr("href").substringAfter(baseUrl)
            chapter.name = it.text()
        }
        return chapter
    }
}

class KMangaIn : Madara("Kissmanga.in", "https://kissmanga.in", "en")

class HunterFansub : Madara("Hunter Fansub", "https://hunterfansub.com", "es") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/biblioteca/page/$page?m_orderby=views", headers)
    override fun popularMangaNextPageSelector() = "div.nav-previous"
    override val popularMangaUrlSelector = "div.post-title a:last-child"
}

class MangaArabTeam : Madara("مانجا عرب تيم Manga Arab Team", "https://mangaarabteam.com", "ar") {
    override fun imageRequest(page: Page): Request {
        return GET(page.imageUrl!!.replace("http:", "https:"))
    }
}

class NightComic : Madara("Night Comic", "https://nightcomic.com", "en") {
    override val formHeaders: Headers = headersBuilder()
        .add("Content-Type", "application/x-www-form-urlencoded")
        .add("X-MOD-SBB-CTYPE", "xhr")
        .build()
}

@Nsfw
class Toonily : Madara("Toonily", "https://toonily.com", "en") {
    override fun getGenreList(): List<Genre> = listOf(
        Genre("Action", "action-webtoon"),
        Genre("Adult", "adult-webtoon"),
        Genre("Adventure", "adventure-webtoon"),
        Genre("Comedy", "comedy-webtoon"),
        Genre("Drama", "drama-webtoon"),
        Genre("Fantasy", "fantasy-webtoon"),
        Genre("Gender Bender", "gender-bender"),
        Genre("Gossip", "gossip"),
        Genre("Harem", "harem-webtoon"),
        Genre("Historical", "webtoon-historical"),
        Genre("Horror", "horror-webtoon"),
        Genre("Josei", "josei-manga"),
        Genre("Mature", "mature-webtoon"),
        Genre("Mystery", "mystery-webtoon"),
        Genre("NTR", "ntr-webtoon"),
        Genre("Psychological", "psychological-webtoon"),
        Genre("Romance", "romance-webtoon"),
        Genre("School life", "school-life-webtoon"),
        Genre("Sci-Fi", "scifi-webtoon"),
        Genre("Seinen", "seinen-webtoon"),
        Genre("Shoujo", "shoujo"),
        Genre("Shounen", "shounen-webtoon"),
        Genre("Slice of Life", "sliceoflife-webtoon"),
        Genre("Supernatural", "supernatural-webtoon"),
        Genre("Thriller", "thriller-webtoon"),
        Genre("Tragedy", "tragedy"),
        Genre("Vanilla", "vanilla-webtoon"),
        Genre("Yaoi", "yaoi-webtoon"),
        Genre("Yuri", "yuri-webtoon")
    )
}

class MangaKomi : Madara("MangaKomi", "https://mangakomi.com", "en")

class Wakamics : Madara("Wakamics", "https://wakamics.com", "en")

class KingzManga : Madara("KingzManga", "https://kingzmanga.com", "ar")

@Nsfw
class YaoiToshokan : Madara("Yaoi Toshokan", "https://yaoitoshokan.com.br", "pt-BR", SimpleDateFormat("dd MMM yyyy", Locale("pt", "BR"))) {
    // Page has custom link to scan website.
    override val popularMangaUrlSelector = "div.post-title a:not([target])"

    // Chapters are listed old to new.
    override fun chapterListParse(response: Response): List<SChapter> {
        return super.chapterListParse(response).reversed()
    }

    override fun pageListParse(document: Document): List<Page> {
        return document.select(pageListParseSelector)
            .mapIndexed { index, element ->
                // Had to add trim because of white space in source.
                val imageUrl = element.select("img").attr("data-src").trim()
                Page(index, document.location(), imageUrl)
            }
    }
}

class GoldenManga : Madara("موقع لترجمة المانجا", "https://golden-manga.com", "ar", SimpleDateFormat("yyyy-MM-dd", Locale.US)) {
    override fun searchMangaSelector() = "div.c-image-hover a"
    override fun searchMangaFromElement(element: Element): SManga {
        return SManga.create().apply {
            setUrlWithoutDomain(element.attr("href"))
            title = element.attr("title")
            thumbnail_url = element.select("img").firstOrNull()?.let { imageFromElement(it) }
        }
    }
    override fun chapterListSelector() = "div.main a"
    override fun chapterFromElement(element: Element): SChapter {
        return SChapter.create().apply {
            setUrlWithoutDomain(element.attr("href"))
            name = element.select("h6:first-of-type").text()
            date_upload = parseChapterDate(element.select("h6:last-of-type").firstOrNull()?.ownText())
        }
    }
}

class Mangalek : Madara("مانجا ليك", "https://mangalek.com", "ar", SimpleDateFormat("MMMM dd, yyyy", Locale("ar")))

class AstralLibrary : Madara("Astral Library", "https://astrallibrary.net", "en") {
    override fun chapterListParse(response: Response): List<SChapter> = super.chapterListParse(response).reversed()
}

class KlikManga : Madara("KlikManga", "https://klikmanga.com", "id", SimpleDateFormat("MMMM dd, yyyy", Locale("id")))

class MiracleScans : Madara("Miracle Scans", "https://miraclescans.com", "en")

class Manhuasnet : Madara("Manhuas.net", "https://manhuas.net", "en")

class MangaTX : Madara("MangaTX", "https://mangatx.com", "en")

class ATMSubs : Madara("ATM-Subs", "https://atm-subs.fr", "fr", SimpleDateFormat("d MMMM yyyy", Locale("fr")))

class OnManga : Madara("OnManga", "https://onmanga.com", "en")

class MangaAction : Madara("Manga Action", "https://manga-action.com", "ar", SimpleDateFormat("yyyy-MM-dd", Locale("ar")))

class NijiTranslations : Madara("Niji Translations", "https://niji-translations.com", "ar", SimpleDateFormat("MMMM dd, yyyy", Locale("ar")))

class IchirinNoHanaYuri : Madara("Ichirin No Hana Yuri", "https://ichirinnohanayuri.com.br", "pt-BR", SimpleDateFormat("dd/MM/yyyy", Locale("pt")))

class LilyManga : Madara("Lily Manga", "https://lilymanga.com", "en", SimpleDateFormat("yyyy-MM-dd", Locale.US))

class MangaBob : Madara("MangaBob", "https://mangabob.com", "en")

class ThreeSixtyFiveManga : Madara("365Manga", "https://365manga.com", "en") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=latest", headers)
}

class DisasterScans : Madara("Disaster Scans", "https://disasterscans.com", "en") {
    override val popularMangaUrlSelector = "div.post-title a:last-child"

    override fun mangaDetailsParse(document: Document): SManga {
        val manga = super.mangaDetailsParse(document)

        with(document) {
            select("div.post-title h1").first()?.let {
                manga.title = it.ownText()
            }
        }

        return manga
    }
}

class MangaDods : Madara("MangaDods", "https://www.mangadods.com", "en", SimpleDateFormat("yyyy-MM-dd", Locale.US))

class MangaStream : Madara("MangaStream", "https://www.mangastream.cc", "en") {
    override fun getGenreList() = listOf(
        Genre("Action", "action-manga"),
        Genre("Adventure", "adventure-manga"),
        Genre("Bara", "bara-manga"),
        Genre("BL Manga", "bl-manga"),
        Genre("Comedy", "comedy-manga"),
        Genre("Comics", "comics-online"),
        Genre("Completed Manga", "completed-manga"),
        Genre("Drama", "drama-manga"),
        Genre("Ecchi", "ecchi-manga"),
        Genre("Fantasy", "fantasy-manga"),
        Genre("Gender Bender", "gender-bender-manga"),
        Genre("Hardcore Yaoi", "hardcore-yaoi"),
        Genre("Harem", "harem-manga"),
        Genre("Hipercool", "hipercool"),
        Genre("Historical", "historical"),
        Genre("Horror", "horror-manga"),
        Genre("Incest", "incest-manga"),
        Genre("Josei", "josei"),
        Genre("Lolicon", "lolicon-manga"),
        Genre("Manga", "manga"),
        Genre("Manhua", "manhua"),
        Genre("Manhwa", "manhwa-manga"),
        Genre("Manhwa Hentai Manga", "manhwahentai"),
        Genre("Martial Arts", "martial-arts-manga"),
        Genre("Mature", "mature-manga"),
        Genre("Mystery", "mystery"),
        Genre("One shot", "one-shot"),
        Genre("Psychological", "psychological-manga"),
        Genre("Rape", "rape-manga"),
        Genre("Reincarnation", "reincarnation-manga"),
        Genre("Reverse Harem", "reverse-harem"),
        Genre("Romance", "romance-manga"),
        Genre("School Life", "read-school-life-manga"),
        Genre("Sci-fi", "sci-fi"),
        Genre("Seinen", "seinen-manga"),
        Genre("Shotacon", "shotacon"),
        Genre("Shoujo", "shoujo-manga"),
        Genre("Shoujo Ai", "shoujo-ai"),
        Genre("Shounen", "shounen-manga"),
        Genre("Shounen Ai", "shounen-ai"),
        Genre("Slice of Life", "slice-of-life"),
        Genre("Smut", "smut-manga"),
        Genre("Soft Yaoi", "soft-yaoi"),
        Genre("Soft Yuri", "soft-yuri"),
        Genre("Sports", "sports-manga"),
        Genre("Supernatural", "supernatural"),
        Genre("Tragedy", "tragedy"),
        Genre("Webtoon", "webtoons"),
        Genre("Yaoi", "yaoi-manga"),
        Genre("Yuri", "yuri-manga")
    )
}

class NeoxScanlator : Madara("Neox Scanlator", "https://neoxscan.net", "pt-BR", SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))) {
    override val client: OkHttpClient = network.cloudflareClient.newBuilder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .build()

    override fun headersBuilder(): Headers.Builder = Headers.Builder()
        .add("User-Agent", USER_AGENT)
        .add("Referer", baseUrl)
        .add("Origin", baseUrl)

    // Only status and order by filter work.
    override fun getFilterList(): FilterList = FilterList(super.getFilterList().slice(3..4))

    companion object {
        private const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36"
    }
}

class MangaLord : Madara("Manga Lord", "https://mangalord.com", "en")

@Nsfw
class PornComix : Madara("PornComix", "https://www.porncomixonline.net", "en")

class PMScans : Madara("PMScans", "http://www.pmscans.com", "en")

class MangaRead : Madara("Manga Read", "https://mangaread.co", "en", SimpleDateFormat("yyyy-MM-dd", Locale.US))

class Manga68 : Madara("Manga68", "https://manga68.com", "en") {
    override val pageListParseSelector = "div.page-break, div.text-left p"
}

class ManhuaBox : Madara("ManhuaBox", "https://manhuabox.net", "en") {
    override val pageListParseSelector = "div.page-break, div.text-left p img"
}

class RaiderScans : Madara("Raider Scans", "https://raiderscans.com", "en")

class PojokManga : Madara("Pojok Manga", "https://pojokmanga.com", "id", SimpleDateFormat("MMM dd, yyyy", Locale.US))

class TopManhua : Madara("Top Manhua", "https://topmanhua.com", "en", SimpleDateFormat("MM/dd/yy", Locale.US)) {
    override fun headersBuilder(): Headers.Builder = super.headersBuilder().add("Referer", baseUrl)
}

class ManyToonClub : Madara("ManyToonClub", "https://manytoon.club", "ko")

class ManhuaUS : Madara("ManhuaUS", "https://manhuaus.com", "en") {
    override val pageListParseSelector = "li.blocks-gallery-item"
}

class MangaWT : Madara("MangaWT", "https://mangawt.com", "tr")

class DecadenceScans : Madara("Decadence Scans", "https://reader.decadencescans.com", "en")

class MangaRockTeam : Madara("Manga Rock Team", "https://mangarockteam.com", "en")

class MixedManga : Madara("Mixed Manga", "https://mixedmanga.com", "en", SimpleDateFormat("d MMM yyyy", Locale.US)) {
    override fun headersBuilder(): Headers.Builder = super.headersBuilder().add("Referer", baseUrl)
}

class ManhuasWorld : Madara("Manhuas World", "https://manhuasworld.com", "en")

class ArazNovel : Madara("ArazNovel", "https://www.araznovel.com", "tr", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())) {
    override fun formBuilder(page: Int, popular: Boolean): FormBody.Builder = super.formBuilder(page, popular)
        .add("vars[meta_query][0][0][value]", "manga")
    override fun getGenreList() = listOf(
        Genre("Aksiyon", "action"),
        Genre("Macera", "adventure"),
        Genre("Cartoon", "cartoon"),
        Genre("Comic", "comic"),
        Genre("Komedi", "comedy"),
        Genre("Yemek", "cooking"),
        Genre("Doujinshi", "doujinshi"),
        Genre("Dram", "drama"),
        Genre("Ecchi", "ecchi"),
        Genre("Fantastik", "fantasy"),
        Genre("Harem", "harem"),
        Genre("Tarihi", "historical"),
        Genre("Korku", "horror"),
        Genre("Manga", "manga"),
        Genre("Manhua", "manhua"),
        Genre("Manhwa", "manhwa"),
        Genre("Olgun", "mature"),
        Genre("Mecha", "mecha"),
        Genre("Yetişkin", "adult"),
        Genre("Gizem", "mystery"),
        Genre("One Shot", "one-shot"),
        Genre("Isekai", "isekai"),
        Genre("Josei", "josei"),
        Genre("Dedektif", "detective"),
        Genre("Karanlık", "smut"),
        Genre("Romantizm", "romance"),
        Genre("Okul Yaşamı", "school-life"),
        Genre("Yaşamdan Kesit", "slice-of-life"),
        Genre("Spor", "sports"),
        Genre("Doğa Üstü", "supernatural"),
        Genre("Trajedi", "tragedy"),
        Genre("Webtoon ", "webtoon"),
        Genre("Dövüş Sanatları ", "martial-arts"),
        Genre("Bilim Kurgu", "sci-fi"),
        Genre("Seinen", "seinen"),
        Genre("Shoujo", "shoujo"),
        Genre("Shoujo Ai", "shoujo-ai"),
        Genre("Shounen", "shounen"),
        Genre("Shounen Ai", "shounen-ai"),
        Genre("Soft Yaoi", "soft-yaoi"),
        Genre("Soft Yuri", "soft-yuri"),
        Genre("Yaoi", "yaoi"),
        Genre("Yuri", "yuri")
    )
    override fun chapterListParse(response: Response): List<SChapter> {
        return getXhrChapters(response.asJsoup().select("div#manga-chapters-holder").attr("data-id")).let { document ->
            document.select("li.parent").let { elements ->
                if (!elements.isNullOrEmpty()) {
                    elements.reversed()
                        .map { volumeElement -> volumeElement.select(chapterListSelector()).map { chapterFromElement(it) } }
                        .flatten()
                } else {
                    document.select(chapterListSelector()).map { chapterFromElement(it) }
                }
            }
        }
    }
}

class ManhwaRaw : Madara("Manhwa Raw", "https://manhwaraw.com", "ko")

class GuncelManga : Madara("GuncelManga", "https://guncelmanga.com", "tr")

class WeScans : Madara("WeScans", "https://wescans.xyz", "en") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manhua/manga/?m_orderby=views", headers)
    override fun popularMangaNextPageSelector(): String? = null
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manhua/manga/?m_orderby=latest", headers)
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList) = GET("$baseUrl/manhua/?s=$query&post_type=wp-manga")
    override fun searchMangaNextPageSelector(): String? = null
    override fun getFilterList(): FilterList = FilterList()
}

class ArangScans : Madara("Arang Scans", "https://www.arangscans.com", "en", SimpleDateFormat("d MMM yyyy", Locale.US)) {
    // has very few manga
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga?m_orderby=views", headers)
    override fun popularMangaNextPageSelector(): String? = null
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga?m_orderby=latest", headers)
    override fun latestUpdatesNextPageSelector(): String? = null
}

@Nsfw
class MangaHentai : Madara("Manga Hentai", "https://mangahentai.me", "en")

class MangaPhoenix : Madara("Manga Diyari", "https://mangadiyari.com", "tr") {
    // Formerly "Manga Phoenix"
    override val id = 4308007020001642101
}

class FirstKissManhua : Madara("1st Kiss Manhua", "https://1stkissmanhua.com", "en", SimpleDateFormat("d MMM yyyy", Locale.US)) {
    override fun imageRequest(page: Page): Request = GET(page.imageUrl!!, headersBuilder().add("Referer", "https://1stkissmanga.com").build())
}

class HeroManhua : Madara("Hero Manhua", "https://heromanhua.com", "en")

class MartialScans : Madara("Martial Scans", "https://martialscans.com", "en") {
    override fun popularMangaFromElement(element: Element): SManga {
        val manga = SManga.create()

        with(element) {
            select(popularMangaUrlSelector).last()?.let {
                manga.setUrlWithoutDomain(it.attr("href"))
                manga.title = it.ownText()
            }

            select("img").last()?.let {
                manga.thumbnail_url = imageFromElement(it)
            }
        }

        return manga
    }
    override fun searchMangaFromElement(element: Element): SManga = popularMangaFromElement(element)
}

class MangaYosh : Madara("MangaYosh", "https://mangayosh.xyz", "id", SimpleDateFormat("dd MMM yyyy", Locale.US))

class WebtoonXYZ : Madara("WebtoonXYZ", "https://www.webtoon.xyz", "en") {
    private fun pagePath(page: Int) = if (page > 1) "page/$page/" else ""
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/webtoons/${pagePath(page)}?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/webtoons/${pagePath(page)}?m_orderby=latest", headers)
}

class MangaReadOrg : Madara("MangaRead.org", "https://www.mangaread.org", "en", SimpleDateFormat("dd.MM.yyy", Locale.US))

class TurkceManga : Madara("Türkçe Manga", "https://turkcemanga.com", "tr") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/page/$page/?s&post_type=wp-manga&m_orderby=views", headers)
    override fun popularMangaSelector() = searchMangaSelector()
    override fun popularMangaFromElement(element: Element): SManga = searchMangaFromElement(element)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/page/$page/?s&post_type=wp-manga&m_orderby=latest", headers)
    override fun latestUpdatesSelector() = searchMangaSelector()
    override fun latestUpdatesFromElement(element: Element): SManga = searchMangaFromElement(element)
}

class EinherjarScan : Madara("Einherjar Scan", "https://einherjarscans.space", "en")

class DoujinYosh : Madara("DoujinYosh", "https://doujinyosh.work", "id") {
    // source issue, doing this limits results to one page but not doing it returns no results at all
    override fun searchPage(page: Int) = ""
    override fun getGenreList() = listOf(
        Genre("4 Koma", "4koma"),
        Genre("Adult", "adult"),
        Genre("Ahegao", "ahegao"),
        Genre("Anal", "anal"),
        Genre("Animal", "animal"),
        Genre("Artist CG", "artist-cg"),
        Genre("Big Breast", "big-breast"),
        Genre("Big Penis", "big-penis"),
        Genre("Bikini", "bikini"),
        Genre("Black Mail", "black-mail"),
        Genre("Blowjob", "blowjob"),
        Genre("Body Swap", "body-swap"),
        Genre("Bondage", "bondage"),
        Genre("Cheating", "cheating"),
        Genre("Crossdressing", "crossdressing"),
        Genre("DILF", "dilf"),
        Genre("Dark Skin", "dark-skin"),
        Genre("Defloration", "defloration"),
        Genre("Demon Girl", "demon-girl"),
        Genre("Doujin", "doujin"),
        Genre("Drugs", "drugs"),
        Genre("Drunk", "drunk"),
        Genre("Elf", "elf"),
        Genre("Famele Only", "famele-only"),
        Genre("Femdom", "femdom"),
        Genre("Filming", "filming"),
        Genre("Footjob", "footjob"),
        Genre("Full Color", "full-color"),
        Genre("Furry", "furry"),
        Genre("Futanari", "futanari"),
        Genre("Glasses", "glasses"),
        Genre("Gore", "gore"),
        Genre("Group", "group"),
        Genre("Gyaru", "gyaru"),
        Genre("Harem", "harem"),
        Genre("Humiliation", "humiliation"),
        Genre("Impregnation", "impregnation"),
        Genre("Incest", "incest"),
        Genre("Inverted Nipples", "inverted-nipples"),
        Genre("Kemomimi", "kemomimi"),
        Genre("Lactation", "lactation"),
        Genre("Loli", "loli"),
        Genre("Lolipai", "lolipai"),
        Genre("MILF", "milf"),
        Genre("Maid", "maid"),
        Genre("Male Only", "male-only"),
        Genre("Miko", "miko"),
        Genre("Mind Break", "mind-break"),
        Genre("Mind Control", "mind-control"),
        Genre("Monster", "monster"),
        Genre("Monster Girl", "monster-girl"),
        Genre("Multi-Work Series", "multi-work-series"),
        Genre("Nakadashi", "nakadashi"),
        Genre("Netorare", "netorare"),
        Genre("Otona (R18)", "otona"),
        Genre("Oyakodon", "oyakodon"),
        Genre("Paizuri", "paizuri"),
        Genre("Pantyhose", "pantyhose"),
        Genre("Pregnant", "pregnant"),
        Genre("Prostitution", "prostitution"),
        Genre("Rape", "rape"),
        Genre("School Uniform", "school-uniform"),
        Genre("Sex Toy", "sex-toy"),
        Genre("Shota", "shota"),
        Genre("Sister", "sister"),
        Genre("Sleep", "sleep"),
        Genre("Slime", "slime"),
        Genre("Small Breast", "small-breast"),
        Genre("Sole Female", "sole-female"),
        Genre("Sole Male", "sole-male"),
        Genre("Stocking", "stocking"),
        Genre("Story Arc", "story-arc"),
        Genre("Sweating", "sweating"),
        Genre("Swimsuit", "swimsuit"),
        Genre("Teacher", "teacher"),
        Genre("Tentacles", "tentacles"),
        Genre("Tomboy", "tomboy"),
        Genre("Tomgirl", "tomgirl"),
        Genre("Torture", "torture"),
        Genre("Twins", "twins"),
        Genre("Virginity", "virginity"),
        Genre("Webtoon", "webtoon"),
        Genre("XRay", "xray"),
        Genre("Yandere", "yandere"),
        Genre("Yaoi", "yaoi"),
        Genre("Yuri", "yuri")
    )
}

class Manga347 : Madara("Manga347", "https://manga347.com", "en", SimpleDateFormat("d MMM, yyyy", Locale.US)) {
    override val pageListParseSelector = "li.blocks-gallery-item"
}
class RenaScans : Madara("Renascence Scans (Renascans)", "https://new.renascans.com", "en", SimpleDateFormat("dd/MM/yyyy", Locale.US))

class QueensManga : Madara("QueensManga ملكات المانجا", "https://queensmanga.com", "ar") {
    override fun chapterListSelector(): String = "div.listing-chapters_wrap a"
}

class DropeScan : Madara("Drope Scan", "https://dropescan.com", "pt-BR") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=views", headers)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga/page/$page/?m_orderby=latest", headers)
}

class TheTopComic : Madara("TheTopComic", "https://thetopcomic.com", "en")

class WebNovel : Madara("WebNovel", "https://webnovel.live", "en")

class TruyenTranhAudioCom : Madara("TruyenTranhAudio.com", "https://truyentranhaudio.com", "vi", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())) {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/page/$page?s&post_type=wp-manga&m_orderby=views", headers)
    override fun popularMangaSelector() = searchMangaSelector()
    override fun popularMangaFromElement(element: Element): SManga = searchMangaFromElement(element)
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/page/$page?s&post_type=wp-manga&m_orderby=latest", headers)
    override fun latestUpdatesSelector() = searchMangaSelector()
    override fun latestUpdatesFromElement(element: Element): SManga = searchMangaFromElement(element)
    override fun pageListParse(document: Document): List<Page> {
        return document.select("div.reading-content img").map { it.attr("abs:src") }
            .filterNot { it.isNullOrEmpty() }
            .distinct()
            .mapIndexed { i, url -> Page(i, "", url) }
    }
}

class TruyenTranhAudioOnline : Madara("TruyenTranhAudio.online", "https://truyentranhaudio.online", "vi", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())) {
    override val formHeaders: Headers = headersBuilder()
        .add("Content-Type", "application/x-www-form-urlencoded")
        .build()
    override fun pageListParse(document: Document): List<Page> {
        return document.select("div.reading-content img").map { it.attr("abs:src") }
            .filterNot { it.isNullOrEmpty() }
            .distinct()
            .mapIndexed { i, url -> Page(i, "", url) }
    }
}

class MangaTurf : Madara("Manga Turf", "https://mangaturf.com", "en")

class SheaManga : Madara("Shea Manga", "https://sheamanga.my.id", "id")

class FurioScans : Madara("Furio Scans", "https://furioscans.com", "pt-BR", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()))

class Mangareceh : Madara("Mangareceh", "https://mangareceh.id", "id")

class ComicKiba : Madara("ComicKiba", "https://comickiba.com", "en") {
    override val pageListParseSelector = "li.blocks-gallery-item img:nth-child(1)"
}

class KlanKomik : Madara("KlanKomik", "https://klankomik.com", "id")

class ToonPoint : Madara("ToonPoint", "https://toonpoint.com", "en") {
    override val userAgentRandomizer = ""
}

class MangaScantrad : Madara("Manga-Scantrad", "https://manga-scantrad.net", "fr", SimpleDateFormat("d MMM yyyy", Locale.FRANCE))

class ManhuaPlus : Madara("Manhua Plus", "https://manhuaplus.com", "en") {
    override val pageListParseSelector = "li.blocks-gallery-item"
}

class AkuManga : Madara("AkuManga", "https://akumanga.com", "ar")

class AsgardTeam : Madara("Asgard Team", "https://www.asgard1team.com", "ar")

class Skymanga : Madara("Skymanga", "https://skymanga.co", "en")

@Nsfw
class ToonilyNet : Madara("Toonily.net", "https://toonily.net", "en")

class BestManga : Madara("BestManga", "https://bestmanga.club", "ru", SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()))

class TwilightScans : Madara("Twilight Scans", "https://twilightscans.com", "en")

class DetectiveConanAr : Madara("شبكة كونان العربية", "https://www.manga.detectiveconanar.com", "ar")

// mostly novels, doesn't include year in chapter dates (even for past years)
class WordExcerpt : Madara("Word Excerpt", "https://wordexcerpt.com", "en") {
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/archive/webtoon/page/$page/?m_orderby=views", headers)
    override fun popularMangaNextPageSelector() = "div.nav-previous a"
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/archive/webtoon/page/$page/?m_orderby=latest", headers)
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request = GET("$baseUrl/?s=the&post_type=wp-manga&genre[]=webtoon", headers)
    override fun getFilterList(): FilterList = FilterList()
}

// mostly novels
class WoopRead : Madara("WoopRead", "https://woopread.com", "en")

class Ninjavi : Madara("Ninjavi", "https://ninjavi.com", "ar")

class ManhwaTop : Madara("Manhwatop", "https://manhwatop.com", "en")

class ImmortalUpdates : Madara("Immortal Updates", "https://immortalupdates.com", "en")

class Bakaman : Madara("Bakaman", "https://bakaman.net", "th")

class CatTranslator : Madara("CAT-translator", "https://cat-translator.com", "th") {
    override fun popularMangaRequest(page: Int): Request =
        POST("$baseUrl/manga/wp-admin/admin-ajax.php", formHeaders, formBuilder(page, true).build(), CacheControl.FORCE_NETWORK)
    override fun latestUpdatesRequest(page: Int): Request =
        POST("$baseUrl/manga/wp-admin/admin-ajax.php", formHeaders, formBuilder(page, false).build(), CacheControl.FORCE_NETWORK)
    override fun searchPage(page: Int): String = "manga/page/$page/"
}

@Nsfw
class ComicsValley : Madara("Comics Valley", "https://comicsvalley.com", "hi")

class FriendlyTranslations : Madara("Friendly Translations", "https://friendlytranslationsscan.website", "en")

class Wakascan : Madara("Wakascan", "https://wakascan.com", "fr")

class ShoujoHearts : Madara("ShoujoHearts", "http://shoujohearts.com", "en") {
    override fun popularMangaRequest(page: Int): Request =
        POST("$baseUrl/reader/wp-admin/admin-ajax.php", formHeaders, formBuilder(page, true).build(), CacheControl.FORCE_NETWORK)
    override fun latestUpdatesRequest(page: Int): Request =
        POST("$baseUrl/reader/wp-admin/admin-ajax.php", formHeaders, formBuilder(page, false).build(), CacheControl.FORCE_NETWORK)
    override fun searchPage(page: Int): String = "reader/page/$page/"
}

class AlianzaMarcial : Madara("AlianzaMarcial", "https://alianzamarcial.xyz", "es")

class OlaoeManga : Madara("مانجا اولاو", "https://olaoe.giize.com", "ar")

class FdmScan : Madara("FDM Scan", "https://fdmscan.com", "pt-BR", SimpleDateFormat("MMMM dd, yyyy", Locale("pt", "BR")))

class ArgosScan : Madara("Argos Scan", "https://argosscan.com", "pt-BR", SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR")))

class OrigamiOrpheans : Madara("Origami Orpheans", "https://origami-orpheans.com.br", "pt-BR")

class DarkyuRealm : Madara("Darkyu Realm", "https://darkyuerealm.site", "pt-BR")

class MangaKiss : Madara("Manga Kiss", "https://mangakiss.org", "en")

class MangaRocky : Madara("Manga Rocky", "https://mangarocky.com", "en")

class JJutsuScans : Madara("JJutsuScans", "https://jjutsuscans.com", "en")

class S2Manga : Madara("S2Manga", "https://s2manga.com", "en")

class MangaLandArabic : Madara("Manga Land Arabic", "https://mangalandarabic.com", "ar")

class Kombatch : Madara("Kombatch", "https://kombatch.com", "id")

class ProjetoScanlator : Madara("Projeto Scanlator", "https://projetoscanlator.com", "pt-BR", SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")))

class HikariScan : Madara("Hikari Scan", "https://hikariscan.com.br", "pt-BR", SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR")))
