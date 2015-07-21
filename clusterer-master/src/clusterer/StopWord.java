package clusterer;

/**
 *
 * @author Gulsah Kandemir
 */
public class StopWord {

    public static String[] stopWords =
    {
        // Generic English Stopwords
        "salsa","a", "about", "above", "above", "across", "after", "afterwards", 
        "again", "against", "all", "almost", "alone", "along", "already", "also",
        "although","always","am","among", "amongst", "amoungst", "amount",  "an", 
        "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", 
        "are", "around", "as",  "at", "back","be","became", "because","become",
        "becomes", "becoming", "been", "before", "beforehand", "behind", "being", 
        "below", "beside", "besides", "between", "beyond", "bill", "both", 
        "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", 
        "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", 
        "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", 
        "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", 
        "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", 
        "find", "fire", "first", "five", "for", "former", "formerly", "forty", 
        "found", "four", "from", "front", "full", "further", "get", "give", "go", 
        "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", 
        "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", 
        "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", 
        "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", 
        "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", 
        "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", 
        "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", 
        "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", 
        "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", 
        "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", 
        "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", 
        "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", 
        "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", 
        "some", "somehow", "someone", "something", "sometime", "sometimes", 
        "somewhere", "still", "such", "system", "take", "ten", "than", "that", 
        "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", 
        "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", 
        "thin", "third", "this", "those", "though", "three", "through", "throughout", 
        "thru", "thus", "to", "together", "top", "toward", "towards", "twelve", 
        "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", 
        "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", 
        "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", 
        "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", 
        "whose", "why", "will", "with", "within", "without", "would", "yet", "you", 
        "your", "yours", "yourself", "yourselves", "the" ,
      
        // Internet Specific Stopwords
        "jar",
        "zip",
        "exe",
        "nbsp",
        "com",
        "link",
        "previous",
        "and",
        "a",
        "web",
        "next",
        "page",
        "of",
        "click",
        "site",
        "topic",
        "here",
        "website",
        "domain",
        "prev",
        "obj",
        "download",
        "tar",
        "homepage",
        "internet",
        "explorer",
        "mozilla",
        "netscape",
        "browser",
        "inicial",
        "voltar",
        "atrás",
        "asp",
        "aspx",
        "jsp",
        "rar",
        "gz2",
        "gz",
        "pdf",
        "doc",
        "ps",
        "ppt",
        "tgz",
        "hqx",
        "bin",
        "cab",
        "zoo",
        "mov",
        "mpg",
        "mpeg",
        "avi",
        "mp3",
        "wav",
        "mid",
        "xls",
        "rm",
        "dll",
        "rpm",
        "class",
        "swf",
        "doc",
        "xls",
        "tex",
        "txt",
        "ps",
        "pdf",
        "xml",
        "xhtml",
        "htm",
        "xsl",
        "xslt",
        "previous",
        "janela",
        "windows",
        "window",
        "press",
        "pressione",
        "pressionar",
        "select",
        "seleccionar",
        "seleccione",
        "frame",
        "site",
        "web",
        "website",
        "frames",
        "mail",
        "email",
        "home",
        "page",
        "pagina",
        "página",
        "http",
        "html",
        "index",
        "links",
        "titulo",
        "título",
        "ftp",
        "w3",
        "www",
        "com",
        "edu",
        "gov",
        "int",
        "mil",
        "net",
        "org",
        "arpa",
        "aero",
        "biz",
        "coop",
        "info",
        "name",
        "pro",
        "ac",
        "ad",
        "ae",
        "af",
        "ag",
        "ai",
        "al",
        "am",
        "an",
        "ao",
        "aq",
        "ar",
        "as",
        "at",
        "au",
        "aw",
        "az",
        "ba",
        "bb",
        "bd",
        "be",
        "bf",
        "bg",
        "bh",
        "bi",
        "bj",
        "bm",
        "bn",
        "bo",
        "br",
        "bs",
        "bt",
        "bv",
        "bw",
        "by",
        "bz",
        "ca",
        "cc",
        "cd",
        "cf",
        "cg",
        "ch",
        "ci",
        "ck",
        "cl",
        "cm",
        "cn",
        "co",
        "cr",
        "cu",
        "cv",
        "cx",
        "cy",
        "cz",
        "de",
        "dj",
        "dk",
        "dm",
        "do",
        "dz",
        "ec",
        "ee",
        "eg",
        "eh",
        "er",
        "es",
        "et",
        "fi",
        "fj",
        "fk",
        "fm",
        "fo",
        "fr",
        "ga",
        "gd",
        "ge",
        "gf",
        "gg",
        "gh",
        "gi",
        "gl",
        "gm",
        "gn",
        "gp",
        "gq",
        "gr",
        "gs",
        "gt",
        "gu",
        "gw",
        "gy",
        "hk",
        "hm",
        "hn",
        "hr",
        "ht",
        "hu",
        "id",
        "ie",
        "il",
        "im",
        "in",
        "io",
        "iq",
        "ir",
        "is",
        "it",
        "je",
        "jm",
        "jo",
        "jp",
        "ke",
        "kg",
        "kh",
        "ki",
        "km",
        "kn",
        "kp",
        "kr",
        "kw",
        "ky",
        "kz",
        "la",
        "lb",
        "lc",
        "li",
        "lk",
        "lr",
        "ls",
        "lt",
        "lu",
        "lv",
        "ly",
        "ma",
        "mc",
        "md",
        "mg",
        "mh",
        "mk",
        "ml",
        "mm",
        "mn",
        "mo",
        "mp",
        "mq",
        "mr",
        "ms",
        "mt",
        "mu",
        "mv",
        "mw",
        "mx",
        "my",
        "mz",
        "na",
        "nc",
        "ne",
        "nf",
        "ng",
        "ni",
        "nl",
        "no",
        "np",
        "nr",
        "nu",
        "nz",
        "om",
        "pa",
        "pe",
        "pf",
        "pg",
        "ph",
        "pk",
        "pl",
        "pm",
        "pn",
        "pr",
        "ps",
        "pt",
        "pw",
        "py",
        "qa",
        "re",
        "ro",
        "ru",
        "rw",
        "sa",
        "sb",
        "sc",
        "sd",
        "se",
        "sg",
        "sh",
        "si",
        "sj",
        "sk",
        "sl",
        "sm",
        "sn",
        "so",
        "sr",
        "st",
        "sv",
        "sy",
        "sz",
        "tc",
        "td",
        "tf",
        "tg",
        "th",
        "tj",
        "tk",
        "tm",
        "tn",
        "to",
        "tp",
        "tr",
        "tt",
        "tv",
        "tw",
        "tz",
        "ua",
        "ug",
        "uk",
        "um",
        "us",
        "uy",
        "uz",
        "va",
        "vc",
        "ve",
        "vg",
        "vi",
        "vn",
        "vu",
        "wf",
        "ws",
        "ye",
        "yt",
        "yu",
        "za",
        "zm",
        "zr",
        "zw"
    };
}
