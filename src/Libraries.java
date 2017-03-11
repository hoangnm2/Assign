import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Libraries {

	public Library[] libraries; // a collection of libraries of type array
	public int numberOfLibraries; // number of libraries in collection
	
	public Libraries(int numOfLibraries) {
		libraries = new Library[numOfLibraries];
		numberOfLibraries = 0; 
	}

	public Library buildLibraryFromFile(String libraryName, String fileName) {

		Library library = new Library(libraryName);

		String path = System.getProperty("user.dir");
		Book book = null;
		String s;

		try (BufferedReader br = new BufferedReader(new FileReader(path + "/src/" + fileName))) {
			// if you run locally on your environment use: new FileReader(path +
			// "/src/" + fileName)

			while ((s = br.readLine()) != null) {
				final String[] values = s.split(",");
				if (values.length != 2) {
					// skip the lines that are no correctly structured.
					continue;
				}
				
				try {
					book = new Book(values[0], Integer.valueOf(values[1]));
					book.setLibrary(library);
					library.addBook(book);
				} catch (final NumberFormatException e) {
					System.err.println("Error parsing at this row: " + s);
				}
				
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		// Add library to libraries
		libraries[numberOfLibraries] = library;
		numberOfLibraries++;
		return library;
	}

	// TODO: 2 library can have same book? Have to change class def here
	//  look for the same book in all libraries and return all the libraries where the book is in the library inventory
	public Library isThereBookInLibraries(Book book) {
		if (book == null || book.getBookName() == null) {
			System.err.println("Bad input. Cant search for non-exist book.");
			return null;
		}
		for (final Library lib : libraries) {
			//TODO: how 2 books considered equal
			for (int i=0; i<lib.getBooks().size(); i++) {
				final Book bk = (Book) lib.getBooks().get(i);
				if (bk != null && book.getBookName().equalsIgnoreCase(bk.getBookName())) {
					//if (!bk.isRented(lib)) {
						return lib;
					//}
					
				}
			}
		}
		
		return null;
	}

	public Library rentBookAvailable(Book book, String requestDate, String dueDate) {
		
		Library foundLibrary = null;

		return foundLibrary;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(); 
		sb.append("Libraries: ").append(numberOfLibraries).append("\n");
		for (final Library lib : libraries) {
			sb.append(lib).append("\n");
		}
		return sb.toString();
	}
	
	
}
